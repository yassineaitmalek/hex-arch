package com.yatmk.test.ports.events;

import com.yatmk.test.ports.domain.events.SchedulerParams;
import java.util.Date;

public interface LocalScheduler {
    public <T> Date schedule(SchedulerParams<T> schedulerParams);

    public boolean unschedule(String group, String jobId);

    public <T> Date reschedule(SchedulerParams<T> schedulerParams);
}
