package com.yatmk.test.adapter.input.rest.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailInput {

    private List<String> to;

    private List<String> copy;

    private String subject;

    private String body;

    private List<MultipartFile> attachments;
}
