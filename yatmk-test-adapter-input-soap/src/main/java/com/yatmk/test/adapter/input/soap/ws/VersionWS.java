package com.yatmk.test.adapter.input.soap.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.yatmk.test.adapter.input.soap.config.properties.AppSoapVersionProperies;
import com.yatmk.test.ports.domain.dto.VersionDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@WebService(serviceName = "version-ws")
public class VersionWS {

  private final AppSoapVersionProperies appSoapVersionProperies;

  @WebMethod(operationName = "getVersion")
  public VersionDTO getVersion() {
    return VersionDTO.builder().version(appSoapVersionProperies.getVersion()).build();
  }

}