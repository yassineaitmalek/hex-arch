package com.yatmk.test.adapter.input.rest.controllers;

import com.yatmk.test.adapter.input.rest.config.AbstractController;
import com.yatmk.test.adapter.input.rest.dto.MailInput;
import com.yatmk.test.ports.domain.dto.MailDTO;
import com.yatmk.test.ports.domain.dto.MailDTO.MailFileDTO;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import com.yatmk.test.ports.events.SendMailEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(MailController.PATH)
@Tag(name = "mail", description = "Operations for Mail")
public class MailController implements AbstractController {

    public static final String PATH = "/api/mail";

    public static final String PATH_FRAGMENT = "/{id}";

    private final SendMailEvent sendMailEvent;

    @Operation(summary = "Create a new mail")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = @Content(
            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
            schema = @Schema(implementation = MailInput.class)
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "Mail sent successfully",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendMail(@ModelAttribute MailInput mailInput) {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(mailInput.getTo());
        mailDTO.setCopy(mailInput.getCopy());
        mailDTO.setSubject(mailInput.getSubject());
        mailDTO.setBody(mailInput.getBody());
        Optional
            .ofNullable(mailInput)
            .map(MailInput::getAttachments)
            .orElseGet(Collections::emptyList)
            .stream()
            .map(file -> {
                try {
                    return MailFileDTO
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
            .ifPresent(mailDTO::setAttachments);

        return noContent(() -> sendMailEvent.send(mailDTO));
    }
}
