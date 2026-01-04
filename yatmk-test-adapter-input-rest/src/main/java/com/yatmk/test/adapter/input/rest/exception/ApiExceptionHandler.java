package com.yatmk.test.adapter.input.rest.exception;

import com.yatmk.test.adapter.input.rest.config.AbstractController;
import com.yatmk.test.ports.domain.exception.ClientSideException;
import com.yatmk.test.ports.domain.exception.ResourceNotFoundException;
import com.yatmk.test.ports.domain.exception.ServerSideException;
import com.yatmk.test.ports.domain.exception.TooManyRequestsException;
import com.yatmk.test.ports.domain.presentation.ApiExceptionResponse;
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
public class ApiExceptionHandler implements AbstractController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleExceptions(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        return internalException(e, request);
    }

    @ExceptionHandler(value = { ServerSideException.class })
    public ResponseEntity<ApiExceptionResponse> handleServerSideException(ServerSideException e, WebRequest request) {
        log.error(e.getMessage());
        return internalException(e, request);
    }

    @ExceptionHandler(value = { ClientSideException.class })
    public ResponseEntity<ApiExceptionResponse> handleClientSideException(ClientSideException e, WebRequest request) {
        log.error(e.getMessage());
        return badRequest(e, request);
    }

    @ExceptionHandler(value = { TooManyRequestsException.class })
    public ResponseEntity<ApiExceptionResponse> handleTooManyRequestsException(
        TooManyRequestsException e,
        WebRequest request
    ) {
        log.error(e.getMessage());
        return tooManyRequests(e, request);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public ResponseEntity<ApiExceptionResponse> handleResourceNotFoundException(
        ResourceNotFoundException e,
        WebRequest request
    ) {
        log.error(e.getMessage());
        return notFound(e, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(
        ConstraintViolationException e,
        WebRequest request
    ) {
        log.error(e.getMessage());
        return badRequest(e, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(
        MethodArgumentNotValidException e,
        WebRequest request
    ) {
        log.error(e.getMessage());
        return badRequest(e, request);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(BindException e, WebRequest request) {
        log.error(e.getMessage());
        return badRequest(e, request);
    }
}
