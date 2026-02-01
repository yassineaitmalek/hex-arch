package com.yatmk.test.ports.domain.dto;

import java.io.InputStream;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {

    private List<String> to;

    private List<String> copy;

    private String subject;

    private String body;

    private List<MailFileDTO> attachments;

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailFileDTO {

        private InputStream inputStream;

        private String contentType;

        private String fileName;

        private Long fileSize;
    }
}
