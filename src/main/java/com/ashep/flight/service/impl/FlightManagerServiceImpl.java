package com.ashep.flight.service.impl;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.service.FlightManagerService;
import com.ashep.flight.service.FlightStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightManagerServiceImpl implements FlightManagerService {

    private final FlightStorage storage;

    @Autowired
    public FlightManagerServiceImpl(final FlightStorage storage) {

        this.storage = storage;
    }

    @Override
    public List<FlightInfoDto> getFlights(final LocalTime start, final LocalTime end, final DayOfWeek dayOfWeek) {

        return Optional.ofNullable(storage.getFlights())
                       .map(allFlights -> allFlights.get(dayOfWeek))
                       // doing + minute because sub map lower point is exclusive, see subMap doc
                       .map(flightsForDay -> flightsForDay.subMap(start, end.plusMinutes(1)))
                       .map(SortedMap::values)
                       .map(ArrayList::new)
                       .orElseThrow(() -> new IllegalStateException("No flights stored"));

    }

    @Override
    public Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> storeSchedule(
            final List<FlightScheduleRow> rawSchedule) {

        Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> allFlights = new HashMap<>();

        rawSchedule.forEach(raw -> {
            final FlightInfoDto flightInfoDto = FlightInfoDto.fromRow(raw);

            raw.getDays().forEach(day -> allFlights.computeIfAbsent(day, ignore -> new TreeMap<>())
                                                   .put(raw.getDepartureTime(), flightInfoDto));
        });

        storage.saveFlights(allFlights);

        return allFlights;
    }
}
