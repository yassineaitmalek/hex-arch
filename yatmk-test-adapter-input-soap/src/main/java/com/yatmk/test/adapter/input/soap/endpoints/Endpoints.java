package com.yatmk.test.adapter.input.soap.endpoints;

import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yatmk.test.adapter.input.soap.ws.TestWS;
import com.yatmk.test.adapter.input.soap.ws.VersionWS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableAutoConfiguration
@RequiredArgsConstructor
public class Endpoints {

  private final TestWS testWS;

  private final VersionWS versionWS;

  private final Bus bus;

  @Bean
  public Endpoint getTestEndpoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, testWS);
    Map<String, Object> properties = endpoint.getProperties();
    properties.put("mtom-enabled", true);
    endpoint.publish("/test");
    return endpoint;
  }

  @Bean
  public Endpoint getVersionEndpoint() {
    EndpointImpl endpoint = new EndpointImpl(bus, versionWS);
    Map<String, Object> properties = endpoint.getProperties();
    properties.put("mtom-enabled", true);
    endpoint.publish("/version");
    return endpoint;
  }

}
