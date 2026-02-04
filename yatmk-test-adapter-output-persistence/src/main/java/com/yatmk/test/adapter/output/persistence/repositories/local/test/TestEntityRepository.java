package com.yatmk.test.adapter.output.persistence.repositories.local.test;

import com.yatmk.test.adapter.output.persistence.mappers.TestEntityMapper;
import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import com.yatmk.test.ports.domain.exception.ResourceNotFoundException;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import com.yatmk.test.ports.domain.test.TestCreation;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.domain.test.TestUpdate;
import com.yatmk.test.ports.output.TestPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestEntityRepository implements TestPort {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JpaTestEntityRepository jpaTestEntityRepository;

    private final ModelMapper modelMapper;

    private final TestEntityMapper testEntityMapper;

    @Override
    public TestDTO save(TestCreation testCreation) {
        return Optional
                .ofNullable(testCreation)
                .map(e -> modelMapper.map(e, TestEntity.class))
                .map(jpaTestEntityRepository::save)
                .map(testEntityMapper::toDTO)
                .orElseThrow(() -> new ServerSideException("error while saving the test"));
    }

    @Override
    public List<TestDTO> getAudit(Long id) {
        return Optional
                .ofNullable(id)
                .map(jpaTestEntityRepository::findRevisions)
                .orElseGet(Revisions::none)
                .map(Revision::getEntity)
                .map(testEntityMapper::toDTO)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public TestDTO get(Long id) {
        return Optional
                .ofNullable(id)
                .flatMap(jpaTestEntityRepository::findById)
                .map(testEntityMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("test with id " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        Optional
                .ofNullable(id)
                .map(jpaTestEntityRepository::findById)
                .map(Optional::get)
                .ifPresent(jpaTestEntityRepository::delete);
    }

    @Override
    public TestDTO update(Long id, TestUpdate update) {
        TestEntity entity = Optional
                .ofNullable(id)
                .map(jpaTestEntityRepository::findById)
                .map(Optional::get)
                .orElseThrow(() -> new ResourceNotFoundException("test with id " + id + " not found"));

        modelMapper.map(update, entity);

        return Optional
                .of(entity)
                .map(jpaTestEntityRepository::save)
                .map(testEntityMapper::toDTO)
                .orElseThrow(() -> new ServerSideException("error while saving the test"));
    }

    private List<TestEntity> findAll() {
        Query query = entityManager.createQuery("SELECT p FROM TestEntity p ", TestEntity.class);
        return query.getResultList();
    }

    private TestEntity findById(Long id) {
        Query query = entityManager.createQuery("SELECT p FROM TestEntity p where p.id = :id", TestEntity.class);
        query.setParameter("id", id);
        return (TestEntity) query.getSingleResult();
    }

    private Long count() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        Root<TestEntity> rt = cq.from(TestEntity.class);
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        Query q = entityManager.createQuery(cq);
        return (Long) q.getSingleResult();
    }
}
