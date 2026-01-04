package com.yatmk.test.adapter.output.persistence.repositories.config;

import com.yatmk.test.adapter.output.persistence.models.config.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.history.RevisionRepository;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity>
    extends AbstractRepository<T, Long>, RevisionRepository<T, Long, Long> {}
