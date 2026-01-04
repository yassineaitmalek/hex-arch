package com.yatmk.test.adapter.output.persistence.repositories.local.test;

import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import com.yatmk.test.adapter.output.persistence.repositories.config.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTestEntityRepository extends BaseRepository<TestEntity> {}
