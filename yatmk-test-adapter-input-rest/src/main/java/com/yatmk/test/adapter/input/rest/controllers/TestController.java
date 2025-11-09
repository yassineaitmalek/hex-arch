package com.yatmk.test.adapter.input.rest.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yatmk.test.adapter.input.rest.config.AbstractController;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

@Hidden
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController implements AbstractController {

}
