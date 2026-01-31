package com.yatmk.test.adapter.events.config;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailAuthenticator extends Authenticator {

    @Value("${spring.mail.username}")
    private String userName;

    @Value("${spring.mail.password}")
    private String password;

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
