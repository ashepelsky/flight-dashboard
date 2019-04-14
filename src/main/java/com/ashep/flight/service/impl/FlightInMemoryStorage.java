package com.ashep.flight.service.impl;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightInfoListDto;
import com.ashep.flight.service.FlightStorage;
import org.springframework.stereotype.Service;

@Service
public class FlightInMemoryStorage implements FlightStorage {

    private Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> flightsStorage = new HashMap<>();

    @Override
    public Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> getFlights() {

        return flightsStorage;
    }

    @Override
    public void saveFlights(final Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> flights) {
        flightsStorage = flights;
    }
}
