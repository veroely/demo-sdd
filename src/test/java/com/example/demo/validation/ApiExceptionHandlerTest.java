package com.example.demo.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.demo.model.ValidationErrorResponse;

class ApiExceptionHandlerTest {

    @Test
    void createsDocumentedInvalidDateResponse() {
        ValidationErrorResponse response = ApiExceptionHandler.invalidDateResponse();

        assertEquals("INVALID_DATE", response.error());
        assertEquals("Date must be provided in ISO-8601 format (yyyy-MM-dd).",
                response.message());
    }
}
