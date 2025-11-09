package com.yatmk.test.adapter.output.persistence.repositories.local;

import org.springframework.stereotype.Repository;

import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import com.yatmk.test.adapter.output.persistence.repositories.config.BaseRepository;

@Repository
public interface TestEntityRepository extends BaseRepository<TestEntity> {

}
