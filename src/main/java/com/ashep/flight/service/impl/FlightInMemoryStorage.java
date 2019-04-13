package com.ashep.flight.service.impl;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.SortedMap;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.service.FlightStorage;

public class FlightInMemoryStorage implements FlightStorage {

    @Override
    public Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> getFlights() {

        return null;
    }

    @Override
    public void saveFlights(final Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> flights) {

    }
}
