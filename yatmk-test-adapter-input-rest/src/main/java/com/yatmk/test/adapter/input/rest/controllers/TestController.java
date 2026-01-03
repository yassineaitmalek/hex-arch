package com.yatmk.test.adapter.input.rest.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yatmk.test.adapter.input.rest.config.AbstractController;
import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.dto.TestUpdate;
import com.yatmk.test.ports.domain.presentation.ApiDataResponse;
import com.yatmk.test.ports.input.TestUseCase;

import lombok.RequiredArgsConstructor;

//@Hidden
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController implements AbstractController {

    private final TestUseCase testUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<TestDTO>> createTest(@RequestBody TestCreation creation) {
        return ok(() -> testUseCase.create(creation));
    }

    @GetMapping(value = "/{id}/audit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<List<TestDTO>>> getAudit(@PathVariable Long id) {
        return ok(() -> testUseCase.getAudit(id));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<TestDTO>> get(@PathVariable Long id) {
        return ok(() -> testUseCase.get(id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return noContent(() -> testUseCase.delete(id));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<TestDTO>> update(@PathVariable Long id, @RequestBody TestUpdate update) {
        return ok(() -> testUseCase.update(id, update));
    }
}
