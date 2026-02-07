package com.yatmk.test.adapter.input.soap.ws;

import org.springframework.stereotype.Component;

import com.yatmk.test.adapter.input.soap.config.properties.AppSoapVersionProperies;
import com.yatmk.test.adapter.input.soap.sei.VersionSEI;
import com.yatmk.test.ports.domain.dto.VersionDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VersionWS implements VersionSEI {

  private final AppSoapVersionProperies appSoapVersionProperies;

  public VersionDTO getVersion() {
    return VersionDTO.builder().version(appSoapVersionProperies.getVersion()).build();
  }

}