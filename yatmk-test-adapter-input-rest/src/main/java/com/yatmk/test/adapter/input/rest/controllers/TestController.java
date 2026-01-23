package com.yatmk.test.adapter.input.rest.controllers;

import com.yatmk.test.adapter.input.rest.config.AbstractController;
import com.yatmk.test.ports.domain.dto.TestCreation;
import com.yatmk.test.ports.domain.dto.TestDTO;
import com.yatmk.test.ports.domain.dto.TestUpdate;
import com.yatmk.test.ports.domain.presentation.ApiDataResponse;
import com.yatmk.test.ports.input.TestUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

//@Hidden
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(TestController.PATH)
@Tag(name = "test", description = "Operations for Test ")
public class TestController implements AbstractController {

    private static final String PATH = "/api/test";

    private static final String PATH_FRAGMENT = "/{id}";

    private final TestUseCase testUseCase;

    @Operation(summary = "Create a new test")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<TestDTO>> createTest(@RequestBody TestCreation creation) {
        return create(() -> testUseCase.create(creation));
    }

    @Operation(summary = "Get test audit by ID")
    @GetMapping(value = PATH_FRAGMENT + "/audit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<List<TestDTO>>> getAudit(@PathVariable Long id) {
        return ok(() -> testUseCase.getAudit(id));
    }

    @Operation(summary = "Get test by ID")
    @GetMapping(value = PATH_FRAGMENT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<TestDTO>> get(@PathVariable Long id) {
        return ok(() -> testUseCase.get(id));
    }

    @Operation(summary = "Delete test by ID")
    @DeleteMapping(value = PATH_FRAGMENT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return noContent(() -> testUseCase.delete(id));
    }

    @Operation(summary = "Update test by ID")
    @PatchMapping(value = PATH_FRAGMENT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiDataResponse<TestDTO>> update(@PathVariable Long id, @RequestBody TestUpdate update) {
        return ok(() -> testUseCase.update(id, update));
    }
}
