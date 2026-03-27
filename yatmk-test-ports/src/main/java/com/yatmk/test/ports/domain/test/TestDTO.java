package com.yatmk.test.ports.domain.test;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {

    private Long id;

    private String attr1;

    private BigInteger attr2;

    private Boolean attr3;

    private BigDecimal attr4;

    public TestDTO(Long id) {
        this.id = id;
        this.attr1 = "test";
        this.attr2 = BigInteger.ONE;
        this.attr3 = Boolean.TRUE;
        this.attr4 = BigDecimal.TEN;
    }
}
