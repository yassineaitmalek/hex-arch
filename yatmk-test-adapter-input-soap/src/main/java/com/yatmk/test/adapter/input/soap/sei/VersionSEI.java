package com.yatmk.test.adapter.input.soap.sei;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.yatmk.test.ports.domain.dto.VersionDTO;

@WebService(serviceName = "version-ws", targetNamespace = "http://ws.soap.input.adapter.test.yatmk.com/")
public interface VersionSEI {

  @WebMethod(operationName = "getVersion")
  public VersionDTO getVersion();

}