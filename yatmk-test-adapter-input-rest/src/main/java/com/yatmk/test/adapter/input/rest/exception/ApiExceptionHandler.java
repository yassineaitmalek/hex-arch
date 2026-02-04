package com.yatmk.test.adapter.input.rest.exception;

import com.yatmk.test.adapter.input.rest.config.AbstractErrorController;
import com.yatmk.test.ports.domain.exception.ClientSideException;
import com.yatmk.test.ports.domain.exception.ResourceNotFoundException;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import com.yatmk.test.ports.domain.exception.TooManyRequestsException;
import com.yatmk.test.ports.domain.presentation.ApiExceptionResponse;

import java.util.UUID;

import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler implements AbstractErrorController {

    private static final String MESSAGE = "An unexpected error occurred with the request. Error ID: %s";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleExceptions(Exception e, WebRequest request) {
        String id = UUID.randomUUID().toString();
        log.error(String.format(MESSAGE, id), e);
        return internalException(e, request, id);
    }

    @ExceptionHandler(value = { ServerSideException.class })
    public ResponseEntity<ApiExceptionResponse> handleServerSideException(ServerSideException e, WebRequest request) {
        String id = UUID.randomUUID().toString();
        log.error(String.format(MESSAGE, id), e);
        return internalException(e, request, id);
    }

    @ExceptionHandler(value = { ClientSideException.class })
    public ResponseEntity<ApiExceptionResponse> handleClientSideException(ClientSideException e, WebRequest request) {
        String id = UUID.randomUUID().toString();
        log.error(String.format(MESSAGE, id), e);
        return badRequest(e, request, id);
    }

    @ExceptionHandler(value = { TooManyRequestsException.class })
    public ResponseEntity<ApiExceptionResponse> handleTooManyRequestsException(
            TooManyRequestsException e,
            WebRequest request) {
        String id = UUID.randomUUID().toString();
        log.error(String.format(MESSAGE, id), e);
        return tooManyRequests(e, request, id);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public ResponseEntity<ApiExceptionResponse> handleResourceNotFoundException(
            ResourceNotFoundException e,
            WebRequest request) {
        String id = UUID.randomUUID().toString();
        log.error(String.format(MESSAGE, id));
        return notFound(e, request, id);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(
            ConstraintViolationException e,
            WebRequest request) {
        String id = UUID.randomUUID().toString();
        log.error(String.format(MESSAGE, id));
        return badRequest(e, request, id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(
            MethodArgumentNotValidException e,
            WebRequest request) {
        String id = UUID.randomUUID().toString();
        log.error(String.format(MESSAGE, id));
        return badRequest(e, request, id);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(BindException e, WebRequest request) {
        String id = UUID.randomUUID().toString();
        log.error(String.format(MESSAGE, id));
        return badRequest(e, request, id);
    }
}
