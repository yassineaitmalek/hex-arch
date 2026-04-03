package com.yatmk.test.common.utility;

import io.vavr.control.Try;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CheckUtility {

    public static DecimalFormat getDecimalFormat() {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df;
    }

    public static DecimalFormat getDecimalFormatXLS() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df;
    }

    public String checkString(String s) {
        return Optional.ofNullable(s).map(String::trim).filter(e -> !e.isEmpty()).orElse(null);
    }

    public String formatDouble(Double d) {
        return Optional.ofNullable(d).map(getDecimalFormat()::format).orElse(null);
    }

    public String formatDoubleXls(Double d) {
        return Optional.ofNullable(d).map(getDecimalFormatXLS()::format).orElse(null);
    }

    public BigDecimal checkBigDecimal(String s) {
        return Try.of(() -> checkString(s)).filter(Objects::nonNull).map(BigDecimal::new).getOrNull();
    }

    public BigInteger checkBigInteger(String s) {
        return Try
            .of(() -> checkString(s))
            .filter(Objects::nonNull)
            .map(BigDecimal::new)
            .map(BigDecimal::toBigInteger)
            .getOrNull();
    }

    public LocalDate checkDate(String s) {
        return Try.of(() -> checkString(s)).filter(Objects::nonNull).map(LocalDate::parse).getOrNull();
    }
}
