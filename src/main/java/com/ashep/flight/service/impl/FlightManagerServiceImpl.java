package com.ashep.flight.service.impl;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.service.FlightManagerService;
import com.ashep.flight.service.FlightStorage;

public class FlightManagerServiceImpl implements FlightManagerService {

    private final FlightStorage storage;

    public FlightManagerServiceImpl(final FlightStorage storage) {

        this.storage = storage;
    }

    @Override
    public List<FlightInfoDto> getFlights(final LocalTime start, final LocalTime end, final DayOfWeek dayOfWeek) {

        return null;
    }

    @Override
    public Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> storeSchedule(
            final List<FlightScheduleRow> rawSchedule) {

        return null;
    }
}
