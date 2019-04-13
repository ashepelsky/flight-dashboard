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

        LocalTime start;
        LocalTime end;

        LocalTime now = LocalTime.now();

        final int hour = now.getHour();

        end = hour > 20 ? LocalTime.MAX : now.plusHours(3);
        start = hour < 3 ? LocalTime.MIN : now.minusHours(3);

        return getFlights(start, end, LocalDate.now().getDayOfWeek());
    }

}
