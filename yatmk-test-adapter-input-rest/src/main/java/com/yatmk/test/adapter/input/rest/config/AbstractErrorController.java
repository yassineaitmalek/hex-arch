package com.yatmk.test.adapter.input.rest.config;

import com.yatmk.test.ports.domain.presentation.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface AbstractErrorController {
    public default ResponseEntity<ApiExceptionResponse> internalException(Exception exception, WebRequest request,
            String id) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiExceptionResponse
                                .builder()
                                .id(id)
                                .message(exception.getMessage())
                                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                                .path(request.getDescription(false))
                                .build());
    }

    public default ResponseEntity<ApiExceptionResponse> badRequest(Exception exception, WebRequest request, String id) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiExceptionResponse
                                .builder()
                                .id(id)
                                .message(exception.getMessage())
                                .httpStatus(HttpStatus.BAD_REQUEST.value())
                                .status(HttpStatus.BAD_REQUEST.toString())
                                .path(request.getDescription(false))
                                .build());
    }

    public default ResponseEntity<ApiExceptionResponse> notFound(Exception exception, WebRequest request, String id) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiExceptionResponse
                                .builder()
                                .id(id)
                                .message(exception.getMessage())
                                .httpStatus(HttpStatus.NOT_FOUND.value())
                                .status(HttpStatus.NOT_FOUND.toString())
                                .path(request.getDescription(false))
                                .build());
    }

    public default ResponseEntity<ApiExceptionResponse> tooManyRequests(Exception exception, WebRequest request,
            String id) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(
                        ApiExceptionResponse
                                .builder()
                                .id(id)
                                .message(exception.getMessage())
                                .httpStatus(HttpStatus.TOO_MANY_REQUESTS.value())
                                .status(HttpStatus.TOO_MANY_REQUESTS.toString())
                                .path(request.getDescription(false))
                                .build());
    }
}
