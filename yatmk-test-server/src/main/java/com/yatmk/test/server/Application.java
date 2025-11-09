package com.yatmk.test.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@ComponentScan(basePackages = {
                "com.yatmk.test.ports",
                "com.yatmk.test.infrastructure",
                "com.yatmk.test.common",
                "com.yatmk.test.adapter.output.persistence",
                "com.yatmk.test.adapter.input.rest"
})
@RequiredArgsConstructor
public class Application {

        public static void main(String[] args) {

                SpringApplication.run(Application.class, args);

        }

}
