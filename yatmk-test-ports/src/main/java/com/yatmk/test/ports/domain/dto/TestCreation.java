package com.yatmk.test.ports.domain.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCreation {

    private String attr1;

    private BigInteger attr2;

    private Boolean attr3;

    private BigDecimal attr4;
}
