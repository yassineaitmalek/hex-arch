package com.yatmk.test.adapter.listener.services;

import com.yatmk.test.adapter.listener.events.MailEvent;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final ApplicationEventPublisher eventPublisher;

    public void send(MailEvent mailEvent) {
        log.info("sending email ...");

        if (Objects.nonNull(mailEvent)) {
            eventPublisher.publishEvent(mailEvent);
        }
    }
}
