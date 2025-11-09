package com.yatmk.test.adapter.output.persistence.repositories.config;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoRepositoryBean
public abstract class AbstractDSLRepository<T, U> extends SimpleJpaRepository<T, U> {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    public AbstractDSLRepository(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

}
