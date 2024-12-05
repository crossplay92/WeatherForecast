package com.weather.forecast.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;

/**
 * This is the controller advice, it will be used for errors handling
 */
@ControllerAdvice
@Slf4j
public class ForecastControllerAdvice {

    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("HttpClientErrorException: {}", ex.getMessage());
        return new ResponseEntity<>(String.format("Some parameters in the request are not correct, error: %s",
                ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("NoSuchElementException: {}", ex.getMessage());
        return new ResponseEntity<>(String.format("NoSuchElementException, error: %s",
                ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
