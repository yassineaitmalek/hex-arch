package com.yatmk.test.adapter.output.persistence.repositories.config;

import org.springframework.data.repository.NoRepositoryBean;

import com.yatmk.test.adapter.output.persistence.models.config.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends AbstractRepository<T, Long> {

}