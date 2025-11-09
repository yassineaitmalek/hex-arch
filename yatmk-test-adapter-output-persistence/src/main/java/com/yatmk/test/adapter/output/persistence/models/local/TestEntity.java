package com.yatmk.test.adapter.output.persistence.models.local;

import javax.persistence.Entity;

import com.yatmk.test.adapter.output.persistence.models.config.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@Entity
@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TestEntity extends BaseEntity {


  private String attr1 ;

  private Integer attr2 ;

  private boolean attr3 ;

  private Double attr4 ;
}
