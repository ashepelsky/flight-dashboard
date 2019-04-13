package com.ashep.flight.model;

import java.time.LocalTime;

public enum FlightStatusEnum {
    ON_TIME,
    DEPARTED;

    public static FlightStatusEnum getStatusFromTime(LocalTime time) {

        if (time.compareTo(LocalTime.now()) < 0) {
            return ON_TIME;
        } else {
            return DEPARTED;
        }
    }
}
