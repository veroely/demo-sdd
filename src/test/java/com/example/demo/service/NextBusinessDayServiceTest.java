package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

class NextBusinessDayServiceTest {

    private final NextBusinessDayService service = new NextBusinessDayService(
            date -> Set.of(LocalDate.of(2026, 5, 25)).contains(date));

    @Test
    void returnsFollowingDayForRegularBusinessDay() {
        assertEquals(LocalDate.of(2026, 9, 8),
                service.calculateNextBusinessDay(LocalDate.of(2026, 9, 7)));
    }

    @Test
    void skipsWeekendAfterFriday() {
        assertEquals(LocalDate.of(2026, 9, 14),
                service.calculateNextBusinessDay(LocalDate.of(2026, 9, 11)));
    }

    @Test
    void skipsWeekendAndConfiguredHoliday() {
        assertEquals(LocalDate.of(2026, 5, 26),
                service.calculateNextBusinessDay(LocalDate.of(2026, 5, 22)));
    }

    @Test
    void movesForwardWhenInputIsWeekend() {
        assertEquals(LocalDate.of(2026, 9, 14),
                service.calculateNextBusinessDay(LocalDate.of(2026, 9, 12)));
    }
}
