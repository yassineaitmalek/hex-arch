package com.yatmk.test.ports.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCreation {

    private String attr1 ;

    private BigInteger attr2 ;

    private Boolean attr3 ;

    private BigDecimal attr4 ;
}
