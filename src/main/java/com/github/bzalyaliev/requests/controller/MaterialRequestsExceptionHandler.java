package com.github.bzalyaliev.requests.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@Slf4j(topic = "MATERIAL_REQUESTS_EXCEPTION_HANDLER")
@RestControllerAdvice
public class MaterialRequestsExceptionHandler extends ResponseEntityExceptionHandler {

    private final Map<Class<? extends Exception>, Integer> exceptionToResponseCode = Map.of(
            HttpClientErrorException.BadRequest.class, 400,
            NotFoundException.class, 404,
            BadRequestException.class, 400
    );

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        if (exceptionToResponseCode.containsKey(exception.getClass())) {
            Integer code = exceptionToResponseCode.get(exception.getClass());
            return ResponseEntity.status(code)
                    .body(ErrorResponse.builder()
                            .type(exception.getClass().getSimpleName())
                            .detailedMessage(exception.getMessage())
                            .status(code)
                            .build());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .type(exception.getClass().getSimpleName())
                            .detailedMessage(exception.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build());
        }
    }
}

