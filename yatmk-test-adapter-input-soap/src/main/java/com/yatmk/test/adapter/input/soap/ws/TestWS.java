package com.yatmk.test.adapter.input.soap.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.yatmk.test.ports.domain.test.TestCreation;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.domain.test.TestUpdate;
import com.yatmk.test.ports.input.TestUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@WebService(serviceName = "test-ws")
public class TestWS {

  private final TestUseCase testUseCase;

  @WebMethod(operationName = "createTest")
  public TestDTO createTest(@WebParam(name = "testCreation") TestCreation testCreation) {
    return testUseCase.create(testCreation);
  }

  @WebMethod(operationName = "deleteTest")
  public boolean deleteTest(@WebParam(name = "id") Long id) {
    testUseCase.delete(id);
    return Boolean.TRUE;
  }

  @WebMethod(operationName = "getTest")
  public TestDTO getTest(@WebParam(name = "id") Long id) {
    return testUseCase.get(id);
  }

  @WebMethod(operationName = "updateTest")
  public TestDTO updateTest(
      @WebParam(name = "id") Long id,
      @WebParam(name = "testUpdate") TestUpdate update) {
    return testUseCase.update(id, update);
  }
}