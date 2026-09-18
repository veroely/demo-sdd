package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.service.ConfiguredHolidayCalendar;
import com.example.demo.service.NextBusinessDayService;
import com.example.demo.validation.DateInputParser;
import com.example.demo.validation.ApiExceptionHandler;

class NextBusinessDayControllerTest {

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        NextBusinessDayService service = new NextBusinessDayService(new ConfiguredHolidayCalendar());
        mockMvc = MockMvcBuilders.standaloneSetup(new NextBusinessDayController(service, new DateInputParser()))
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void returnsNextBusinessDayForRegularDate() throws Exception {
        mockMvc.perform(get("/api/business-days/next").param("date", "2026-09-07"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inputDate").value("2026-09-07"))
                .andExpect(jsonPath("$.nextBusinessDay").value("2026-09-08"));
    }

    @Test
    void skipsWeekendAndHoliday() throws Exception {
        mockMvc.perform(get("/api/business-days/next").param("date", "2026-05-22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nextBusinessDay").value("2026-05-26"));
    }
}
