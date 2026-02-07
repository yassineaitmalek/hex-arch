package com.yatmk.test.adapter.input.soap.ws;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.yatmk.test.adapter.input.soap.sei.TestSEI;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.input.TestUseCase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestWSIntegrationTest {

  @LocalServerPort
  private int port;

  private TestSEI client;

  @MockBean
  private TestUseCase testUseCase;

  @BeforeEach
  public void setup() {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setServiceClass(TestSEI.class);

    // Constructs:
    String address = "http://localhost:" + port + "/yatmk-api-test/ws/test";

    factory.setAddress(address);
    client = (TestSEI) factory.create();
  }

  @Test
  public void testGetOperationOverSoap() {
    // Arrange
    TestDTO mockDto = new TestDTO();
    mockDto.setId(1L);
    Mockito.when(testUseCase.get(1L)).thenReturn(mockDto);

    // Act
    TestDTO response = client.getTest(1L);

    // Assert
    Assertions.assertNotNull(response);
    Assertions.assertEquals(1L, response.getId());
  }
}