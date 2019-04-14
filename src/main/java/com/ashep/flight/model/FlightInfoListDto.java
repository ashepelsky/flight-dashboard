package com.ashep.flight.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FlightInfoListDto {

    private LocalTime departureTime;
    private List<FlightInfoDto> infoDtos = new ArrayList<>();

    public FlightInfoListDto(final LocalTime departureTime) {

        this.departureTime = departureTime;
    }
}
