package com.yatmk.test.adapter.output.persistence.models.local;

import javax.persistence.Entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.yatmk.test.adapter.output.persistence.models.config.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

//@Entity
//@Data
//@With
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
//@Where(clause = "deleted = false")
//@SQLDelete(sql = "UPDATE SoftDeleteEntity  SET deleted = 1 WHERE id = ? and version = ?")
//public class SoftDeleteEntity extends BaseEntity {
//
//  @Builder.Default
//  private boolean deleted = Boolean.FALSE;
//
//}
