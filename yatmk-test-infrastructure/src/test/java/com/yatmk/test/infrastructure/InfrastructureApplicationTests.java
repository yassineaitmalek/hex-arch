package com.yatmk.test.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootConfiguration
@EnableAutoConfiguration
public class InfrastructureApplicationTests {

    @Test
    public void contextLoads() {
        // This is an empty test that only verifies if the Spring application context
        // loads successfully.
        Assertions.assertTrue(true);
    }
}
