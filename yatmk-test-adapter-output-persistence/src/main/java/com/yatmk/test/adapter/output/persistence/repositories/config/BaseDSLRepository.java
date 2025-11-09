package com.yatmk.test.adapter.output.persistence.repositories.config;

import javax.persistence.EntityManager;

import org.springframework.data.repository.NoRepositoryBean;

import com.yatmk.test.adapter.output.persistence.models.config.BaseEntity;

@NoRepositoryBean
public abstract class BaseDSLRepository<T extends BaseEntity> extends AbstractDSLRepository<T, Long> {

  public BaseDSLRepository(Class<T> domainClass, EntityManager entityManager) {
    super(domainClass, entityManager);

  }

}