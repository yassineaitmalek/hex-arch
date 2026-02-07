package com.yatmk.test.adapter.input.soap.sei;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.yatmk.test.ports.domain.test.TestCreation;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.domain.test.TestUpdate;

@WebService(serviceName = "test-ws", targetNamespace = "http://ws.soap.input.adapter.test.yatmk.com/")
public interface TestSEI {

  @WebMethod(operationName = "createTest")
  public TestDTO createTest(@WebParam(name = "testCreation") TestCreation testCreation);

  @WebMethod(operationName = "deleteTest")
  public boolean deleteTest(@WebParam(name = "id") Long id);

  @WebMethod(operationName = "getTest")
  public TestDTO getTest(@WebParam(name = "id") Long id);

  @WebMethod(operationName = "updateTest")
  public TestDTO updateTest(
      @WebParam(name = "id") Long id,
      @WebParam(name = "testUpdate") TestUpdate update);
}