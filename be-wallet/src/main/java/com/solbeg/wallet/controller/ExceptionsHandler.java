package com.solbeg.wallet.controller;

import com.solbeg.wallet.dto.ErrorResponse;
import com.solbeg.wallet.exception.WalletException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        var customErrorResponse = ErrorResponse.builder()
                .errors(errors)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .date(LocalDateTime.now())
                .build();

        return handleExceptionInternal(
                ex, customErrorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> generalException(Exception e) {
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .errors(List.of("Server error"))
                .date(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletException.class)
    protected ResponseEntity<?> walletException(Exception e) {
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .date(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build(), HttpStatus.BAD_REQUEST);
    }
}

