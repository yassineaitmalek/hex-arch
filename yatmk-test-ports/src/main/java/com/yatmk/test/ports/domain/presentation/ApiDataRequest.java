package com.yatmk.test.ports.domain.presentation;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiDataRequest {

    private String serviceName;

    private String originalApp;

    private String targetApp;

    private String canal;

    private String protocol;

    private String method;

    private String uri;

    @Builder.Default
    private String requestID = UUID.randomUUID().toString();

    @Builder.Default
    private LocalDateTime start = LocalDateTime.now();
}
