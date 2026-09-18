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

class NextBusinessDayValidationTest {

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        NextBusinessDayService service = new NextBusinessDayService(new ConfiguredHolidayCalendar());
        mockMvc = MockMvcBuilders.standaloneSetup(new NextBusinessDayController(service, new DateInputParser()))
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void rejectsImpossibleDate() throws Exception {
        mockMvc.perform(get("/api/business-days/next").param("date", "2026-02-30"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("INVALID_DATE"));
    }

    @Test
    void rejectsEmptyDate() throws Exception {
        mockMvc.perform(get("/api/business-days/next").param("date", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        "Date must be provided in ISO-8601 format (yyyy-MM-dd)."));
    }

    @Test
    void rejectsMissingDate() throws Exception {
        mockMvc.perform(get("/api/business-days/next"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("INVALID_DATE"));
    }
}
