package com.ashep.flight.model;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public class FlightInfoDto {

    private final LocalTime departureTime;

    private final String destination;

    private final String destinationAirport;

    private final String flightNo;

    private final FlightStatusEnum status;

    public FlightInfoDto(LocalTime departureTime,
                         String destination,
                         String destinationAirport,
                         String flightNo) {

        this.departureTime = departureTime;
        this.destination = destination;
        this.destinationAirport = destinationAirport;
        this.flightNo = flightNo;
        status = FlightStatusEnum.getStatusFromTime(departureTime);
    }

    public static FlightInfoDto fromRow(FlightScheduleRow flightScheduleRow) {

        return new FlightInfoDto(flightScheduleRow.getDepartureTime(),
                                 flightScheduleRow.getDestination(),
                                 flightScheduleRow.getDestinationAirport(),
                                 flightScheduleRow.getFlightNo());
    }
}
