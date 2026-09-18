package com.example.demo.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.model.ValidationErrorResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);
    private static final String INVALID_DATE_MESSAGE =
            "Date must be provided in ISO-8601 format (yyyy-MM-dd).";

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ValidationErrorResponse> handleInvalidDate() {
        LOGGER.warn("Rejected invalid date input");
        return ResponseEntity.badRequest().body(invalidDateResponse());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ValidationErrorResponse> handleMissingParameter() {
        LOGGER.warn("Rejected request with missing date parameter");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidDateResponse());
    }

    public static ValidationErrorResponse invalidDateResponse() {
        return new ValidationErrorResponse("INVALID_DATE", INVALID_DATE_MESSAGE);
    }
}
