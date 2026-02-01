package com.yatmk.test.adapter.events.events;

import java.io.InputStream;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class MailEvent extends ApplicationEvent {

    private List<String> to;

    private List<String> copy;

    private String subject;

    private String body;

    private List<MailFile> attachments;

    /**
     * @param to
     * @param copy
     * @param subject
     * @param body
     * @param files
     */
    public MailEvent(
        Object source,
        List<String> to,
        List<String> copy,
        String subject,
        String body,
        List<MailFile> attachments
    ) {
        super(source);
        this.to = to;
        this.copy = copy;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailFile {

        private InputStream inputStream;

        private String contentType;

        private String fileName;

        private Long fileSize;
        // case multipartFile : new ByteArrayDataSource(file.getBytes(),
        // file.getContentType());
        // case local file : new FileDataSource(file);

    }
}
