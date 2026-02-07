package com.yatmk.test.server;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.yatmk.test.server",
        "com.yatmk.test.ports",
        "com.yatmk.test.infrastructure",
        "com.yatmk.test.common",
        "com.yatmk.test.adapter.output.persistence",
        "com.yatmk.test.adapter.events",
        "com.yatmk.test.adapter.input.rest",
        "com.yatmk.test.adapter.input.soap",
})
@RequiredArgsConstructor
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
