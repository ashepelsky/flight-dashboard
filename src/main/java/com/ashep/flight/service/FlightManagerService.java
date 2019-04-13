package com.ashep.flight.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightScheduleRow;

public interface FlightManagerService {

    List<FlightInfoDto> getFlights(LocalTime start, LocalTime end, DayOfWeek dayOfWeek);

    Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> storeSchedule(List<FlightScheduleRow> rawSchedule);

    default List<FlightInfoDto> getClosestFlights() {

        return getFlights(LocalTime.now().minusHours(2), LocalTime.now().plusHours(3), LocalDate.now().getDayOfWeek());
    }

}
