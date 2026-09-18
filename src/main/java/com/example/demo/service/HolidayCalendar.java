package com.example.demo.service;

import java.time.LocalDate;

@FunctionalInterface
public interface HolidayCalendar {

    boolean isHoliday(LocalDate date);
}
