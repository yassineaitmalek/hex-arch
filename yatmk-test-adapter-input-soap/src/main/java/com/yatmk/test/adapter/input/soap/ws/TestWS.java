package com.yatmk.test.adapter.input.soap.ws;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.yatmk.test.adapter.input.soap.sei.TestSEI;
import com.yatmk.test.ports.domain.test.TestCreation;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.domain.test.TestUpdate;
import com.yatmk.test.ports.input.TestUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@WebService(endpointInterface = "com.yatmk.test.adapter.input.soap.sei.TestSEI", serviceName = "test-ws", targetNamespace = "http://ws.soap.input.adapter.test.yatmk.com/")
public class TestWS implements TestSEI {

  private final TestUseCase testUseCase;

  @Override
  public TestDTO createTest(TestCreation testCreation) {
    return testUseCase.create(testCreation);
  }

  @Override
  public boolean deleteTest(Long id) {
    testUseCase.delete(id);
    return Boolean.TRUE;
  }

  @Override
  public TestDTO getTest(Long id) {
    return testUseCase.get(id);
  }

  @Override
  public TestDTO updateTest(
      Long id,
      TestUpdate update) {
    return testUseCase.update(id, update);
  }
}