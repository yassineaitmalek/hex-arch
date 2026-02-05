package com.yatmk.test.adapter.input.soap.endpoints;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Configuration;

import com.yatmk.test.adapter.input.soap.ws.TestWS;

@Configuration
public class TestEndpoint extends EndpointImpl {

  public TestEndpoint(Bus bus, TestWS implementor) {
    super(bus, implementor);
    this.getProperties().put("mtom-enabled", true);
    this.publish("/test");
  }

}
