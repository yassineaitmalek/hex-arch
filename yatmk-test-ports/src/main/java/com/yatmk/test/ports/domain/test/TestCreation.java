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
public class TestCreation {

    private String attr1;

    private BigInteger attr2;

    private Boolean attr3;

    private BigDecimal attr4;
}
