package com.ashep.flight.service.impl;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.ashep.flight.model.FlightInfoListDto;
import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.service.FlightManagerService;
import com.ashep.flight.service.FlightScheduleParserService;
import com.ashep.flight.service.FlightStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
@Slf4j
public class FlightManagerServiceImpl implements FlightManagerService {

    private final FlightStorage storage;

    private final FlightScheduleParserService parserService;

    @Autowired
    public FlightManagerServiceImpl(final FlightStorage storage, final FlightScheduleParserService parserService) {

        this.parserService = parserService;
        this.storage = storage;
    }

    @Override
    public List<FlightInfoListDto> getFlights(final LocalTime start, final LocalTime end, final DayOfWeek dayOfWeek) {

        return Optional.ofNullable(storage.getFlights())
                       .map(allFlights -> allFlights.get(dayOfWeek))
                       .map(flightsForDay -> flightsForDay.subMap(start, end))
                       .map(SortedMap::values)
                       .map(ArrayList::new)
                       // empty list if no flights
                       .orElseGet(ArrayList::new);
    }

    @Override
    public Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> storeSchedule(
            final List<FlightScheduleRow> rawSchedule) {

        Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> allFlights = new HashMap<>();

        rawSchedule.forEach(raw -> {
            final FlightInfoDto flightInfoDto = FlightInfoDto.fromRow(raw);

            raw.getDays().forEach(day -> allFlights.computeIfAbsent(day, ignore -> new TreeMap<>())
                                                   .computeIfAbsent(raw.getDepartureTime(), FlightInfoListDto::new)
                                                   .getInfoDtos()
                                                   .add(flightInfoDto));
        });

        storage.saveFlights(allFlights);
        return allFlights;
    }

    @PostConstruct
    private void setupInitialFlights() throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:config/initialFlights.csv");

        final FileInputStream inputStream = new FileInputStream(file);
        final List<FlightScheduleRow> scheduleRows = parserService.parseFlightsSchedule(inputStream);
        storeSchedule(scheduleRows);

        log.info("Pre loaded default flights from local file!");
    }
}
