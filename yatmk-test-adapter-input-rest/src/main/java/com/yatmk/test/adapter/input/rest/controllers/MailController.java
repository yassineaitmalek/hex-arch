package com.yatmk.test.adapter.input.rest.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yatmk.test.adapter.input.rest.config.AbstractResponseController;
import com.yatmk.test.adapter.input.rest.dto.MailInput;
import com.yatmk.test.ports.domain.events.Mail;
import com.yatmk.test.ports.domain.events.Mail.MailFile;
import com.yatmk.test.ports.domain.events.SchedulerParams;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import com.yatmk.test.ports.events.LocalScheduler;
import com.yatmk.test.ports.events.SendMailEvent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(MailController.PATH)
@Tag(name = "mail", description = "Operations for Mail")
public class MailController implements AbstractResponseController {

    public static final String PATH = "/api/mail";

    public static final String PATH_FRAGMENT = "/{id}";

    private final SendMailEvent sendMailEvent;

    private final LocalScheduler localScheduler;

    @Operation(summary = "Create a new mail")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = MailInput.class)))
    @ApiResponse(responseCode = "200", description = "Mail sent successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendMail(@ModelAttribute MailInput mailInput) {
        Mail mail = new Mail();
        mail.setTo(mailInput.getTo());
        mail.setCopy(mailInput.getCopy());
        mail.setSubject(mailInput.getSubject());
        mail.setBody(mailInput.getBody());
        Optional
                .ofNullable(mailInput)
                .map(MailInput::getAttachments)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(file -> {
                    try {
                        return MailFile
                                .builder()
                                .fileName(file.getOriginalFilename())
                                .contentType(file.getContentType())
                                .inputStream(file.getInputStream())
                                .fileSize(file.getSize())
                                .build();
                    } catch (Exception e) {
                        throw new ServerSideException("Error processing file: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::ofNullable))
                .ifPresent(mail::setAttachments);

        return noContent(() -> sendMailEvent.send(mail));
    }

    @Operation(summary = "Create a new mail Scheduled")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = MailInput.class)))
    @ApiResponse(responseCode = "200", description = "Mail sent successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PutMapping(value = "/scheduled", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendMailScheduled(@ModelAttribute MailInput mailInput) {
        Mail mail = new Mail();
        mail.setTo(mailInput.getTo());
        mail.setCopy(mailInput.getCopy());
        mail.setSubject(mailInput.getSubject());
        mail.setBody(mailInput.getBody());
        Optional
                .ofNullable(mailInput)
                .map(MailInput::getAttachments)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(file -> {
                    try {
                        return MailFile
                                .builder()
                                .fileName(file.getOriginalFilename())
                                .contentType(file.getContentType())
                                .inputStream(file.getInputStream())
                                .fileSize(file.getSize())
                                .build();
                    } catch (Exception e) {
                        throw new ServerSideException("Error processing file: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::ofNullable))
                .ifPresent(mail::setAttachments);

        LocalDateTime starTime = LocalDateTime.now().plusSeconds(50);

        SchedulerParams<Mail> schedulerParams = SchedulerParams.<Mail>builder()
                .object(mail)
                .jobName("mailJob")
                .mapKey("mail")
                .jobId(UUID.randomUUID().toString())
                .group("MAIL_GROUP")
                .description("scheduel mail")
                .startAt(ZonedDateTime.of(starTime, ZoneId.of("Africa/Casablanca")))
                .reccurent(Boolean.FALSE)
                .build();

        return noContent(() -> localScheduler.schedule(schedulerParams));
    }
}
