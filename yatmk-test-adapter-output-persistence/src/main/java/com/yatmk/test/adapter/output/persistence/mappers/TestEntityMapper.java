package com.yatmk.test.adapter.output.persistence.mappers;

import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.dto.TestUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TestEntityMapper {
    public static final TestEntityMapper INSTANCE = Mappers.getMapper(TestEntityMapper.class);

    TestDTO toDTO(TestEntity entity);

    TestEntity toEntity(TestCreation creation);

    TestEntity toEntity(TestUpdate update);
}
