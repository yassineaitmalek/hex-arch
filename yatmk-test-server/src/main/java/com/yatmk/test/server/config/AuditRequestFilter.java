package com.yatmk.test.server.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuditRequestFilter extends OncePerRequestFilter {

    private static final String TEMPLATE =
        "FINISHED PROCESSING : METHOD = {}; REQUEST URI = {}; REQUEST PAYLOAD = {}; RESPONSE CODE = {}; TIME TAKEN = {}ms";

    public String getOrigin(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;
        String requestBody = getRequest(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        responseWrapper.copyBodyToResponse();
        log.info(TEMPLATE, request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), timeTaken);
    }

    private String getRequest(byte[] contentAsByteArray, String characterEncoding) throws UnsupportedEncodingException {
        return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
    }
}
