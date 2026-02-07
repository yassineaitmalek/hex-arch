package com.yatmk.test.adapter.input.soap.endpoints;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yatmk.test.adapter.input.soap.ws.TestWS;
import com.yatmk.test.adapter.input.soap.ws.VersionWS;

@Configuration
public class Endpoints {

  @Bean
  public Endpoint getTestEndpoint(Bus bus, TestWS implementor) {
    EndpointImpl endpoint = new EndpointImpl(bus, implementor);
    endpoint.getProperties().put("mtom-enabled", true);
    endpoint.publish("/test");
    return endpoint;
  }

  @Bean
  public Endpoint getVersionEndpoint(Bus bus, VersionWS implementor) {
    EndpointImpl endpoint = new EndpointImpl(bus, implementor);
    endpoint.getProperties().put("mtom-enabled", true);
    endpoint.publish("/version");
    return endpoint;
  }

}
