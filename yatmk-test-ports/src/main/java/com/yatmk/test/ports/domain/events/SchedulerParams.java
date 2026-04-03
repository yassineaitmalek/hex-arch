package com.yatmk.test.ports.domain.events;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedulerParams<T> {

    private T object;

    private String jobName;

    private String mapKey;

    private String jobId;

    private String group;

    private String description;

    private ZonedDateTime startAt;

    private boolean reccurent;

    private String cron;
}
