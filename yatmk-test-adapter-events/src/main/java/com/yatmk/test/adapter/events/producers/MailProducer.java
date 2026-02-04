package com.yatmk.test.adapter.events.producers;

import com.yatmk.test.adapter.events.events.MailEvent;
import com.yatmk.test.ports.domain.events.Mail;
import com.yatmk.test.ports.events.SendMailEvent;
import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailProducer implements SendMailEvent {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void send(Mail mail) {
        log.info("sending email ...");

        if (Objects.nonNull(mail)) {
            MailEvent mailEvent = new MailEvent(
                    this,
                    mail.getTo(),
                    mail.getCopy(),
                    mail.getSubject(),
                    mail.getBody(),
                    Collections.emptyList());

            eventPublisher.publishEvent(mailEvent);
        }
    }
}
