package com.yatmk.test.adapter.output.persistence.repositories.local.dsl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yatmk.test.adapter.output.persistence.config.LocalDB;
import com.yatmk.test.adapter.output.persistence.models.local.SoftDeleteEntity;
import com.yatmk.test.adapter.output.persistence.repositories.config.BaseDSLRepository;

@Repository
public class SoftDeleteEntityDSLRepository extends BaseDSLRepository<SoftDeleteEntity> {

  public SoftDeleteEntityDSLRepository(@Qualifier(LocalDB.EMF) EntityManager entityManager) {
    super(SoftDeleteEntity.class, entityManager);

  }

}