package com.yatmk.test.adapter.output.persistence.repositories.local;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.yatmk.test.adapter.output.persistence.mappers.TestEntityMapper;
import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.output.SaveTestPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TestEntityRepository implements SaveTestPort {

    private final JpaTestEntityRepository jpaTestEntityRepository;

    private final ModelMapper modelMapper;

    private final TestEntityMapper testEntityMapper;

    @Override
    public TestDTO saveTest(TestCreation testCreation) {
        TestEntity testEntity = modelMapper.map(testCreation, TestEntity.class);
        testEntity = jpaTestEntityRepository.save(testEntity);
        return testEntityMapper.toDTO(testEntity);
    }
}