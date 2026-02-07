package com.yatmk.test.adapter.input.soap.endpoints;

import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yatmk.test.adapter.input.soap.sei.TestSEI;
import com.yatmk.test.adapter.input.soap.sei.VersionSEI;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableAutoConfiguration
@RequiredArgsConstructor
public class Endpoints {

  private final TestSEI testsSEI;

  private final VersionSEI versionSEI;

  private final Bus bus;

  @Bean
  public Endpoint getTestEndpoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, testsSEI);
    Map<String, Object> properties = endpoint.getProperties();
    properties.put("mtom-enabled", true);
    endpoint.publish("/test");
    return endpoint;
  }

  @Bean
  public Endpoint getVersionEndpoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, versionSEI);
    Map<String, Object> properties = endpoint.getProperties();
    properties.put("mtom-enabled", true);
    endpoint.publish("/version");
    return endpoint;
  }

}
