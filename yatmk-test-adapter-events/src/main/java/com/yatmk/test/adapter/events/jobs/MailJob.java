package com.yatmk.test.adapter.events.jobs;

import java.util.Optional;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.yatmk.test.adapter.events.producers.MailProducer;
import com.yatmk.test.ports.domain.events.Mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("mailJob")
@RequiredArgsConstructor
public class MailJob extends QuartzJobBean {

  private final MailProducer mailProducer;

  public <T> T getObjectFromJobDataMap(JobDataMap jobDataMap, String key, Class<T> clazz) {
    return Optional.ofNullable(key)
        .filter(jobDataMap::containsKey)
        .map(jobDataMap::get)
        .filter(clazz::isInstance)
        .map(clazz::cast)
        .orElse(null);
  }

  @Override
  protected void executeInternal(@NonNull JobExecutionContext jobExecutionContext)
      throws JobExecutionException {

    log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

    JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

    Mail mail = getObjectFromJobDataMap(jobDataMap, "mail", Mail.class);
    if (mail == null) {
      log.error("Mail object is null in JobDataMap");
      throw new JobExecutionException("Mail object is missing");
    }

    mailProducer.send(mail);
  }

}