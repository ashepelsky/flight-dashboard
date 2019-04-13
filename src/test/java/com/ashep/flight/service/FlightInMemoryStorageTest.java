package com.ashep.flight.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.model.FlightStatusEnum;
import com.ashep.flight.service.impl.FlightInMemoryStorage;
import com.ashep.flight.service.impl.FlightManagerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightInMemoryStorageTest {

    private static final String DESTINATION = "Destination";

    private static final String AIRPORT = "Airport";

    private static final String FLIGHT_NO = "No 123";

    private static final LocalTime DEPARTURE_TIME = LocalTime.of(12, 0);

    @InjectMocks
    private FlightInMemoryStorage storage;

    @Test
    public void saveAndGet() {

        final Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> given = getScheduleMap();

        storage.saveFlights(given);

        final Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> result = storage.getFlights();

        assertEquals("Maps for input and output should be the same object", given, result);

        FlightInfoDto flightInfoDto = result.get(DayOfWeek.MONDAY).get(DEPARTURE_TIME);

        assertEquals(DEPARTURE_TIME, flightInfoDto.getDepartureTime());
        assertEquals(DESTINATION, flightInfoDto.getDestination());
        assertEquals(AIRPORT, flightInfoDto.getDestinationAirport());
        assertEquals("no 0", flightInfoDto.getFlightNo());
        assertEquals(FlightStatusEnum.DEPARTED, flightInfoDto.getStatus());

    }

    public static Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> getScheduleMap() {

        List<FlightInfoDto> list = new ArrayList<>();

        Map<DayOfWeek, SortedMap<LocalTime, FlightInfoDto>> map = new HashMap<>();

        SortedMap<LocalTime, FlightInfoDto> sortedMap = new TreeMap<>();

        for (int i = 0; i < 5; i++) {
            final FlightInfoDto infoDto =
                    new FlightInfoDto(DEPARTURE_TIME.plusHours(i), DESTINATION, AIRPORT, "no " + i);

            sortedMap.put(infoDto.getDepartureTime(), infoDto);

        }

        // shuffle to check order in sorted set
        Collections.shuffle(list);

        map.put(DayOfWeek.MONDAY, sortedMap);

        return map;

    }

}
