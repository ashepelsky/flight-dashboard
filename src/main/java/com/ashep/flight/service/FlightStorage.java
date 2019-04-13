package com.ashep.flight.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightScheduleRow;

public interface FlightStorage {

    Map<DayOfWeek, SortedSet<FlightInfoDto>> getFlights();

    void saveFlights(Map<DayOfWeek, SortedSet<FlightInfoDto>> flights);

}
