package com.yatmk.test.adapter.input.soap.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.apache.cxf.endpoint.ServerRegistry;
import org.apache.cxf.endpoint.Server;

import com.yatmk.test.adapter.input.soap.sei.TestSEI;
import com.yatmk.test.ports.domain.test.TestDTO;
import com.yatmk.test.ports.input.TestUseCase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "cxf.path=/ws", "server.servlet.context-path=/yatmk-api-test"
})
public class TestWSIntegrationTest {

  @LocalServerPort
  private int port;

  private TestSEI client;

  @Autowired
  private Bus cxfBus;

  @MockBean
  private TestUseCase testUseCase;

  private String getAddress() {
    return "http://localhost:" + port + "/ws/test";
  }

  // @BeforeEach
  private void printEndpoints() {
    System.out.println("--------------------------------");
    ServerRegistry registry = cxfBus.getExtension(ServerRegistry.class);
    List<Server> servers = registry.getServers();

    System.out.println("--- DISCOVERED CXF ENDPOINTS ---");
    for (Server server : servers) {
      System.out.println("Endpoint Address: " + server.getEndpoint().getEndpointInfo().getAddress());
    }
    System.out.println("--------------------------------");
  }

  @BeforeEach
  public void setup() {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setServiceClass(TestSEI.class);
    factory.setAddress(getAddress());
    Map<String, Object> properties = new HashMap<>();
    properties.put("mtom-enabled", true);
    factory.setProperties(properties);
    client = (TestSEI) factory.create();
    printEndpoints();

  }

  @Test
  public void testGetOperationOverSoap() {
    // Arrange
    Long id = 1L;
    TestDTO uiDTO = new TestDTO(id, "obj", BigInteger.ONE, Boolean.TRUE, BigDecimal.valueOf(5.2));

    Mockito.when(testUseCase.get(id)).thenReturn(uiDTO);

    // Act
    TestDTO response = client.getTest(id);

    // Assert
    Assertions.assertNotNull(response);
    Assertions.assertEquals(id, response.getId());
  }
}