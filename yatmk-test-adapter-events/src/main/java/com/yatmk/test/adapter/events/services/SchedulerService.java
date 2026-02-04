package com.yatmk.test.adapter.events.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.yatmk.test.ports.domain.events.SchedulerParams;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import com.yatmk.test.ports.events.LocalScheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class SchedulerService implements LocalScheduler {

  private final Scheduler scheduler;

  private final Map<String, QuartzJobBean> quartzJobs;

  @Override
  public <T> Date schedule(SchedulerParams<T> schedulerParams) {
    try {

      JobDetail jobDetail = buildJobDetail(schedulerParams);
      Trigger trigger = buildJobTrigger(jobDetail, schedulerParams);
      return scheduler.scheduleJob(jobDetail, trigger);

    } catch (Exception e) {
      throw new ServerSideException(e);
    }

  }

  @Override
  public boolean unschedule(String group, String jobId) {

    try {

      if (Objects.isNull(jobId) || Objects.isNull(group)) {
        return false;
      }
      TriggerKey triggerKey = TriggerKey.triggerKey(jobId, group);
      Trigger trigger = scheduler.getTrigger(triggerKey);
      if (Objects.isNull(trigger)) {
        return false;
      }
      return scheduler.unscheduleJob(triggerKey);
    } catch (Exception e) {
      throw new ServerSideException(e);
    }

  }

  @Override
  public <T> Date reschedule(SchedulerParams<T> schedulerParams) {
    boolean unschedule = unschedule(schedulerParams.getGroup(), schedulerParams.getJobId());
    if (!unschedule) {
      throw new ServerSideException(
          "Unable to reschedule job. No existing job found with id: " + schedulerParams.getJobId());
    }
    return schedule(schedulerParams);

  }

  private Class<? extends Job> getJobClass(String jobName) {

    return Optional.ofNullable(jobName)
        .map(quartzJobs::get)
        .map(QuartzJobBean::getClass)
        .orElseThrow(() -> new ServerSideException("No job found with name: " + jobName));
  }

  private <T> JobDetail buildJobDetail(SchedulerParams<T> schedulerParams) {
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put(schedulerParams.getMapKey(), schedulerParams.getObject());
    Class<? extends Job> jobClass = getJobClass(schedulerParams.getJobName());

    return JobBuilder.newJob(jobClass)
        .withIdentity(schedulerParams.getJobId(), schedulerParams.getGroup())
        .withDescription(schedulerParams.getDescription())
        .usingJobData(jobDataMap)
        .storeDurably()
        .build();
  }

  private ScheduleBuilder<?> getSchedule(boolean isRecurrent, String cron) {
    if (isRecurrent) {
      if (Objects.isNull(cron) || cron.isEmpty()) {
        throw new ServerSideException("Cron expression must be provided for recurrent jobs");
      }
      return CronScheduleBuilder.cronSchedule(cron);
    }
    return SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow();

  }

  private <T> Trigger buildJobTrigger(JobDetail jobDetail, SchedulerParams<T> schedulerParams) {

    return TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity(schedulerParams.getJobId(), schedulerParams.getGroup())
        .withDescription(schedulerParams.getDescription())
        .startAt(Date.from(schedulerParams.getStartAt().toInstant()))
        .withSchedule(getSchedule(schedulerParams.isReccurent(), schedulerParams.getCron()))
        .build();

  }

}