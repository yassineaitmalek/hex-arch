package com.yatmk.test.adapter.events.services;

import com.yatmk.test.adapter.events.events.MailEvent;
import com.yatmk.test.adapter.events.events.MailEvent.MailFile;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${spring.mail.username}")
    private final String from;

    private final Session session;

    private final JavaMailSender mailSender;

    public static final long EMAIL_LIMIT_SIZE = 20l * 1024 * 1024;

    public void send(MailEvent mailEvent) {
        try {
            MimeMessage message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();

            // add From
            message.setFrom(from);

            addDest(mailEvent, message);

            // add Subject
            addSubject(mailEvent, message);

            // add the text part
            sendText(mailEvent, multipart, message);

            // add the attachments part
            sendFiles(mailEvent, multipart, message);

            // send Message
            mailSender.send(message);
        } catch (Exception e) {
            throw new ServerSideException(e);
        }
    }

    private MimeMessage addSubject(MailEvent mailEvent, MimeMessage message) throws MessagingException {
        try {
            message.setSubject(mailEvent.getSubject());
            return message;
        } catch (Exception e) {
            throw new ServerSideException(e);
        }
    }

    private MimeMessage addDest(MailEvent mailEvent, MimeMessage message) {
        try {
            List<String> to = Optional
                .ofNullable(mailEvent.getTo())
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());

            for (String dest : to) {
                message.addRecipients(RecipientType.TO, dest);
            }

            List<String> cc = Optional
                .ofNullable(mailEvent.getCopy())
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());

            for (String dest : cc) {
                message.addRecipients(RecipientType.CC, dest);
            }

            return message;
        } catch (Exception e) {
            throw new ServerSideException(e);
        }
    }

    private MimeMessage sendText(MailEvent mailEvent, Multipart multipart, MimeMessage message) {
        try {
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(mailEvent.getBody(), "text/html; charset=utf-8");
            multipart.addBodyPart(textBodyPart);
            return message;
        } catch (Exception e) {
            throw new ServerSideException(e);
        }
    }

    private Long getAttachmentsSize(MailEvent mailEvent) {
        return Optional
            .ofNullable(mailEvent)
            .map(MailEvent::getAttachments)
            .orElseGet(Collections::emptyList)
            .stream()
            .filter(Objects::nonNull)
            .map(MailFile::getFileSize)
            .reduce(0l, (a, b) -> a + b);
    }

    private MimeMessage sendFiles(MailEvent mailEvent, Multipart multipart, MimeMessage message) {
        try {
            Long totalSize = getAttachmentsSize(mailEvent);
            if (totalSize >= EMAIL_LIMIT_SIZE) {
                throw new ServerSideException("The total attachment size exceeds the limit");
            }

            Optional
                .ofNullable(mailEvent)
                .map(MailEvent::getAttachments)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .forEach(e -> sendFile(e, multipart));

            message.setContent(multipart);
            return message;
        } catch (Exception e) {
            throw new ServerSideException(e);
        }
    }

    private Multipart sendFile(MailFile mailFile, Multipart multipart) {
        try {
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource dataSource = new ByteArrayDataSource(mailFile.getInputStream(), mailFile.getContentType());
            attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
            attachmentBodyPart.setFileName(mailFile.getFileName());
            attachmentBodyPart.setDisposition(Part.ATTACHMENT);
            multipart.addBodyPart(attachmentBodyPart);
            return multipart;
        } catch (Exception e) {
            throw new ServerSideException(e);
        }
    }
}
