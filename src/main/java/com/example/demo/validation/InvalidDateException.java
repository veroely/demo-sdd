package com.example.demo.validation;

public class InvalidDateException extends RuntimeException {

    public InvalidDateException() {
        super("Date must be provided in ISO-8601 format (yyyy-MM-dd).");
    }
}
