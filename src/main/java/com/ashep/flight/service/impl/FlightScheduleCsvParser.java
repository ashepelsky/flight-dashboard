package com.ashep.flight.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.service.FlightScheduleParserService;

public class FlightScheduleCsvParser implements FlightScheduleParserService {

    @Override
    public List<FlightScheduleRow> parseFlightsSchedule(final InputStream inputStream) {

        List<FlightScheduleRow> rawFlights = new ArrayList<>();

        Reader reader = new InputStreamReader(inputStream);

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);

                final FlightScheduleRow scheduleRow = extractFlightData(values);

                rawFlights.add(scheduleRow);

            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getSimpleName())
                  .severe("Failed to parse CVS file. error: " + e.getMessage());
            throw new IllegalArgumentException("Invalid CVS file");
        }

        return rawFlights;
    }

    private FlightScheduleRow extractFlightData(final String[] values) {

        final FlightScheduleRow scheduleRow = new FlightScheduleRow();

        scheduleRow.setDepartureTime(LocalTime.parse(values[0]));
        scheduleRow.setDestination(values[1]);
        scheduleRow.setDestinationAirport(values[2]);
        scheduleRow.setFlightNo(values[3]);

        // last of cvs values are week days boolean placeholders
        final String[] daysArray = Arrays.copyOfRange(values, 4, values.length);

        List<DayOfWeek> flightDays = extractWeekDays(daysArray);
        scheduleRow.setDays(flightDays);

        return scheduleRow;
    }

    private List<DayOfWeek> extractWeekDays(final String[] daysArray) {

        List<DayOfWeek> flightDays = new ArrayList<>();

        // in cvs fist element is sunday
        if (!daysArray[0].isEmpty()) {
            flightDays.add(DayOfWeek.SUNDAY);
        }

        for (int i = 1; i < daysArray.length; i++) {
            if (!daysArray[i].isEmpty()) {
                flightDays.add(DayOfWeek.of(i));
            }
        }
        return flightDays;
    }

}
