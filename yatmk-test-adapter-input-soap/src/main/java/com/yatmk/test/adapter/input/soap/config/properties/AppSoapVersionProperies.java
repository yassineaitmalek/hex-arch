package com.yatmk.test.adapter.input.soap.config.properties;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.application")
public class AppSoapVersionProperies {

    private String version;

    private String name;

    private String title;
}
