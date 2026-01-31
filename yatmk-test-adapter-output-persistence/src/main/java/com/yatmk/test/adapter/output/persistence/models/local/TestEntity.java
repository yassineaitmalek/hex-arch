package com.yatmk.test.adapter.output.persistence.models.local;

import com.yatmk.test.adapter.output.persistence.models.config.BaseEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.hibernate.envers.Audited;

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

    private static final String TABLE_PREFIX = "TAB_";

    @Column(name = TABLE_PREFIX + "ATTR1")
    private String attr1;

    @Column(name = TABLE_PREFIX + "ATTR2")
    private BigInteger attr2;

    @Column(name = TABLE_PREFIX + "ATTR3")
    private Boolean attr3;

    @Column(name = TABLE_PREFIX + "ATTR4")
    private BigDecimal attr4;
}
