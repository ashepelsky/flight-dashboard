package com.ashep.flight.service.impl;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.service.FlightManagerService;

public class FlightManagerServiceImpl implements FlightManagerService {

    @Override
    public List<FlightInfoDto> getFlights(final LocalTime start, final LocalTime end, final DayOfWeek dayOfWeek) {

        return null;
    }

    @Override
    public void storeSchedule(final List<FlightScheduleRow> rawSchedule) {

    }
}
