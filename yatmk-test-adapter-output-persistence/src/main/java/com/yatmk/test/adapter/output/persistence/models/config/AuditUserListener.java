package com.yatmk.test.adapter.output.persistence.models.config;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditUserListener {

  @PrePersist
  public void onPrePersist(Object entity) {
    if (!(entity instanceof BaseEntity)) {
      return;
    }
    BaseEntity base = (BaseEntity) entity;

    base.setCreatedBy("userCreation");
    base.setLastModifiedBy("userCreation");

  }

  @PreUpdate
  public void onPreUpdate(Object entity) {
    if (!(entity instanceof BaseEntity)) {
      return;
    }
    BaseEntity base = (BaseEntity) entity;

    base.setLastModifiedBy("userModification");
  }

}
