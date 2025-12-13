package com.yatmk.test.adapter.output.persistence.repositories.local.test;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.yatmk.test.adapter.output.persistence.mappers.TestEntityMapper;
import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.exception.ServerSideException;
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
        return Optional.ofNullable(testCreation)
                .map(e -> modelMapper.map(e, TestEntity.class))
                .map(jpaTestEntityRepository::save)
                .map(testEntityMapper::toDTO)
                .orElseThrow(() -> new ServerSideException("error while saving the test"));

    }
}