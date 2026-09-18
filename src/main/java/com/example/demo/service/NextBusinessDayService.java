package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NextBusinessDayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NextBusinessDayService.class);
    private final HolidayCalendar holidayCalendar;

    public NextBusinessDayService(HolidayCalendar holidayCalendar) {
        this.holidayCalendar = holidayCalendar;
    }

    public LocalDate calculateNextBusinessDay(LocalDate inputDate) {
        LocalDate candidate = inputDate.plusDays(1);
        while (!isBusinessDay(candidate)) {
            candidate = candidate.plusDays(1);
        }
        LOGGER.info("Calculated next business day: inputDate={}, nextBusinessDay={}", inputDate, candidate);
        return candidate;
    }

    private boolean isBusinessDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        boolean weekend = DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
        return !weekend && !holidayCalendar.isHoliday(date);
    }
}
