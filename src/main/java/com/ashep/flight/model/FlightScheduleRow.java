package com.ashep.flight.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class FlightScheduleRow {

    LocalTime departureTime;

    String destination;

    String destinationAirport;

    String flightNo;

    List<DayOfWeek> days;

}
