package com.yatmk.test.adapter.input.rest.controllers;

import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.presentation.ApiDataResponse;
import com.yatmk.test.ports.input.CreateTestUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.yatmk.test.adapter.input.rest.config.AbstractController;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

//@Hidden
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController implements AbstractController {

    private final CreateTestUseCase createTestUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<TestDTO>> createTest(@RequestBody TestCreation testCreation){
        return ok(() -> createTestUseCase.createTest(testCreation));
    }

}
