package com.yatmk.test.ports.domain.test;

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
public class TestDTO {

    private Long id;

    private String attr1;

    private BigInteger attr2;

    private Boolean attr3;

    private BigDecimal attr4;
}
