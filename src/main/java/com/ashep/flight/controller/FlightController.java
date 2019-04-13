package com.ashep.flight.controller;

import com.ashep.flight.service.FlightManagerService;
import com.ashep.flight.service.FlightScheduleParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {

    private final FlightManagerService flightManager;

    private final FlightScheduleParserService parserService;

    @Autowired
    public FlightController(final FlightManagerService flightManager,
                            final FlightScheduleParserService parserService) {

        this.flightManager = flightManager;
        this.parserService = parserService;
    }

    @GetMapping
    public ResponseEntity test() {
        return ResponseEntity.ok().body("test");
    }

}
