package com.ashep.flight.service.impl;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.SortedSet;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.service.FlightStorage;

public class FlightInMemoryStorage implements FlightStorage {

    @Override
    public Map<DayOfWeek, SortedSet<FlightInfoDto>> getFlights() {

        return null;
    }

    @Override
    public void saveFlights(final Map<DayOfWeek, SortedSet<FlightInfoDto>> flights) {

    }
}
