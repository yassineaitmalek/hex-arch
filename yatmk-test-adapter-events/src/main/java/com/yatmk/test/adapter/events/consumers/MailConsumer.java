package com.yatmk.test.adapter.events.consumers;

import com.yatmk.test.adapter.events.events.MailEvent;
import com.yatmk.test.adapter.events.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailConsumer {

    private final MailService mailService;

    @EventListener
    @Async
    public void execute(MailEvent mailEvent) {
        mailService.send(mailEvent);
    }
}
