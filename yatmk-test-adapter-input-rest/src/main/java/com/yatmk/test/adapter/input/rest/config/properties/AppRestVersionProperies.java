package com.yatmk.test.adapter.input.rest.config.properties;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.application")
public class AppRestVersionProperies {

    private String version;

    private String name;

    private String title;
}
