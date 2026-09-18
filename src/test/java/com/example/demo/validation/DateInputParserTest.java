package com.example.demo.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DateInputParserTest {

    private final DateInputParser parser = new DateInputParser();

    @Test
    void parsesStrictIsoDate() {
        assertEquals(LocalDate.of(2026, 9, 7), parser.parse("2026-09-07"));
    }

    @Test
    void rejectsImpossibleDate() {
        assertThrows(InvalidDateException.class, () -> parser.parse("2026-02-30"));
    }

    @Test
    void rejectsMissingAndNonIsoValues() {
        assertThrows(InvalidDateException.class, () -> parser.parse(null));
        assertThrows(InvalidDateException.class, () -> parser.parse(""));
        assertThrows(InvalidDateException.class, () -> parser.parse("09/07/2026"));
        assertThrows(InvalidDateException.class, () -> parser.parse("2026-09-07T00:00:00Z"));
    }
}
