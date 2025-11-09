package com.yatmk.test.adapter.input.rest.controllers;

import com.yatmk.test.adapter.input.rest.config.AbstractController;
import com.yatmk.test.adapter.input.rest.config.properties.AppVersionProperies;
import com.yatmk.test.ports.domain.dto.VersionDTO;
import com.yatmk.test.ports.domain.presentation.ApiDataResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Tag(name = "version")
@RequiredArgsConstructor
@RequestMapping("/api/version")
public class VersionController implements AbstractController {

  private final AppVersionProperies appVersionProperies;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiDataResponse<VersionDTO>> getVersion() {
    return ok(() -> VersionDTO.builder().version(appVersionProperies.getVersion()).build());
  }

}
