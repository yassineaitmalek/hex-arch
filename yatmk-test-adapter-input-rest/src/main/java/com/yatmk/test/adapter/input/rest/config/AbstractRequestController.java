package com.yatmk.test.adapter.input.rest.config;

import com.yatmk.test.ports.domain.exception.ClientSideException;
import com.yatmk.test.ports.domain.presentation.ApiDataRequest;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public interface AbstractRequestController {
    public default ApiDataRequest request() {
        return Optional
            .ofNullable(RequestContextHolder.getRequestAttributes())
            .filter(ServletRequestAttributes.class::isInstance)
            .map(ServletRequestAttributes.class::cast)
            .map(ServletRequestAttributes::getRequest)
            .map(this::create)
            .orElseThrow(() -> new ClientSideException("API request is not available"));
    }

    public default ApiDataRequest create(HttpServletRequest request) {
        HttpHeaders headers = extractHeaders(request);

        return ApiDataRequest
            .builder()
            .serviceName(getHeader(headers, "serviceName"))
            .originalApp(getHeader(headers, "originalApp"))
            .targetApp(getHeader(headers, "targetApp"))
            .canal(getHeader(headers, "canal"))
            .protocol(request.getScheme())
            .method(request.getMethod())
            .uri(request.getRequestURL().toString())
            .build();
    }

    public default String getHeader(HttpHeaders headers, String header) {
        return Optional
            .ofNullable(header)
            .map(headers::getFirst)
            .orElseThrow(() -> new ClientSideException(header + " header is missing"));
    }

    public default HttpHeaders extractHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if (Objects.nonNull(name)) {
                headers.add(name, request.getHeader(name));
            }
        }
        return headers;
    }
}
