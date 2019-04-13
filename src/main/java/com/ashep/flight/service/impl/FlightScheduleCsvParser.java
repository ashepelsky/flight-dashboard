package com.ashep.flight.service.impl;

import java.io.InputStream;
import java.util.List;

import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.service.FlightScheduleParserService;

public class FlightScheduleCsvParser implements FlightScheduleParserService {

    @Override
    public List<FlightScheduleRow> parseFlightsSchedule(final InputStream inputStream) {

        return null;
    }
}
