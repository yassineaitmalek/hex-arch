package com.yatmk.test.adapter.output.persistence.repositories.local.dsl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yatmk.test.adapter.output.persistence.config.LocalDB;
import com.yatmk.test.adapter.output.persistence.models.local.TestEntity;
import com.yatmk.test.adapter.output.persistence.repositories.config.BaseDSLRepository;
//
//@Repository
//public class TestEntityDSLRepository extends BaseDSLRepository<TestEntity> {
//
//  public TestEntityDSLRepository(@Qualifier(LocalDB.EMF) EntityManager entityManager) {
//    super(TestEntity.class, entityManager);
//
//  }
//
//}