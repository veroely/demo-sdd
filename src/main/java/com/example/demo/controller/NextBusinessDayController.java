package com.example.demo.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.NextBusinessDayResponse;
import com.example.demo.service.NextBusinessDayService;
import com.example.demo.validation.DateInputParser;

@RestController
@RequestMapping("/api/business-days")
public class NextBusinessDayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NextBusinessDayController.class);
    private final NextBusinessDayService service;
    private final DateInputParser dateInputParser;

    public NextBusinessDayController(NextBusinessDayService service, DateInputParser dateInputParser) {
        this.service = service;
        this.dateInputParser = dateInputParser;
    }

    @GetMapping("/next")
    public NextBusinessDayResponse next(@RequestParam(name = "date") String date) {
        LocalDate inputDate = dateInputParser.parse(date);
        LocalDate nextBusinessDay = service.calculateNextBusinessDay(inputDate);
        LOGGER.info("Serving next business day request: inputDate={}", inputDate);
        return new NextBusinessDayResponse(inputDate.toString(), nextBusinessDay.toString());
    }
}
