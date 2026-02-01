package com.yatmk.test.ports.events;

import com.yatmk.test.ports.domain.dto.MailDTO;

public interface SendMailEvent {
    public void send(MailDTO mailDTO);
}
