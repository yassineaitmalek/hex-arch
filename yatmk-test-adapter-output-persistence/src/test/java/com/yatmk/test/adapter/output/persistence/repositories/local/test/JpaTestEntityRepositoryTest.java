package com.yatmk.test.adapter.output.persistence.repositories.local.test;

import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class JpaTestEntityRepositoryTest {

    @Autowired
    private JpaTestEntityRepository jpaTestEntityRepository;

    @BeforeEach
    public void before() {
        TestEntity entity = new TestEntity("Test Name", BigInteger.ONE, Boolean.FALSE, BigDecimal.TEN);
        entity.setId(1L);
        entity.setCreatedBy("test");
        entity.setLastModifiedBy("test");
        jpaTestEntityRepository.save(entity);
    }

    @AfterEach
    public void after() {
        jpaTestEntityRepository.deleteAll();
    }

    private long getId() {
        return jpaTestEntityRepository
                .findAll()
                .stream()
                .findAny()
                .map(TestEntity::getId)
                .orElseThrow(() -> new RuntimeException("No id found"));
    }

    @Test
    public void testFindAll() {
        List<TestEntity> entities = jpaTestEntityRepository.findAll();

        Assertions.assertFalse(entities.isEmpty());
    }

    @Test
    public void testFindById() {
        Long id = getId();
        Optional<TestEntity> entity = jpaTestEntityRepository.findById(id);
        Assertions.assertTrue(entity.isPresent());
        Assertions.assertNotNull(entity.get().getAttr1());
        Assertions.assertNotNull(entity.get().getAttr2());
        Assertions.assertNotNull(entity.get().getAttr3());
        Assertions.assertNotNull(entity.get().getAttr4());
    }

    @Test
    public void testDeleteById() {
        Long id = getId();

        jpaTestEntityRepository.deleteById(id);

        Optional<TestEntity> entity = jpaTestEntityRepository.findById(id);

        Assertions.assertFalse(entity.isPresent());
    }
}
