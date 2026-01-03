package com.yatmk.test.adapter.output.persistence.models.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorUserAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {

    return Optional.ofNullable("user");

  }
}