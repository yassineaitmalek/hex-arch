package com.yatmk.test.ports.events;

import java.util.Date;

import com.yatmk.test.ports.domain.events.SchedulerParams;

public interface LocalScheduler {

  public <T> Date schedule(SchedulerParams<T> schedulerParams);

  public boolean unschedule(String group, String jobId);

  public <T> Date reschedule(SchedulerParams<T> schedulerParams);

}
