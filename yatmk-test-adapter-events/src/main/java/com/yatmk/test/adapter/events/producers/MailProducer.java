package com.yatmk.test.adapter.events.producers;

import com.yatmk.test.adapter.events.events.MailEvent;
import com.yatmk.test.ports.domain.dto.MailDTO;
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
    public void send(MailDTO mailDTO) {
        log.info("sending email ...");

        if (Objects.nonNull(mailDTO)) {
            MailEvent mailEvent = new MailEvent(
                this,
                mailDTO.getTo(),
                mailDTO.getCopy(),
                mailDTO.getSubject(),
                mailDTO.getBody(),
                Collections.emptyList()
            );

            eventPublisher.publishEvent(mailEvent);
        }
    }
}
