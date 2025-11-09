package com.yatmk.test.adapter.output.persistence.models.local;

import com.yatmk.test.adapter.output.persistence.models.config.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Getter
@Setter
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TEST_ENTITY_TABLE")
public class TestEntity extends BaseEntity {

    @Column(name = "ATTR1")
    private String attr1 ;

    @Column(name = "ATTR2")
    private BigInteger attr2 ;

    @Column(name = "ATTR3")
    private Boolean attr3 ;

    @Column(name = "ATTR4")
    private BigDecimal attr4 ;
}
