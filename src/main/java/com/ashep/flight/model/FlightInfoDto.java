package com.ashep.flight.model;

import java.time.LocalTime;

import com.sun.org.apache.xpath.internal.operations.String;
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
}
