package com.yatmk.test.adapter.input.soap.endpoints;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Configuration;

import com.yatmk.test.adapter.input.soap.ws.VersionWS;

@Configuration
public class VersionEndpoint extends EndpointImpl {

  public VersionEndpoint(Bus bus, VersionWS implementor) {
    super(bus, implementor);
    this.getProperties().put("mtom-enabled", true);
    this.publish("/version");
  }

}
