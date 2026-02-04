package com.yatmk.test.ports.events;

import com.yatmk.test.ports.domain.events.Mail;

public interface SendMailEvent {
    public void send(Mail mail);
}
