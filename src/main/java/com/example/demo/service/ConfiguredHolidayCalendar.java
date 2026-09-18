package com.example.demo.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ConfiguredHolidayCalendar implements HolidayCalendar {

    private final Set<LocalDate> holidayDates = Set.of(LocalDate.of(2026, 5, 25));

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidayDates.contains(date);
    }
}
