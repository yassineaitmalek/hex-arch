package com.yatmk.test.adapter.output.persistence.repositories.local;

import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.output.SaveTestPort;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestEntityRepository implements SaveTestPort {

    private  final JpaTestEntityRepository jpaTestEntityRepository;

    private final ModelMapper modelMapper;


    @Override
    public TestDTO saveTest(TestCreation testCreation) {
        TestEntity testEntity = modelMapper.map(testCreation, TestEntity.class);
        testEntity = jpaTestEntityRepository.save(testEntity);
        return modelMapper.map(testEntity, TestDTO.class);

    }
}