package com.ashep.flight.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.SortedMap;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightInfoListDto;

public interface FlightStorage {

    Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> getFlights();

    void saveFlights(Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> flights);

}
