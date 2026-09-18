package com.example.demo.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class DateInputParser {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.ROOT)
            .withResolverStyle(ResolverStyle.STRICT);

    public LocalDate parse(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidDateException();
        }
        try {
            return LocalDate.parse(value, ISO_DATE);
        } catch (DateTimeParseException exception) {
            throw new InvalidDateException();
        }
    }
}
