package com.yatmk.test.adapter.output.persistence.models.local;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.yatmk.test.adapter.output.persistence.models.config.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Entity
@Getter
@Setter
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Table(name = "TEST_ENTITY_TABLE")
public class TestEntity extends BaseEntity {

    @Column(name = "ATTR1")
    private String attr1;

    @Column(name = "ATTR2")
    private BigInteger attr2;

    @Column(name = "ATTR3")
    private Boolean attr3;

    @Column(name = "ATTR4")
    private BigDecimal attr4;
}
