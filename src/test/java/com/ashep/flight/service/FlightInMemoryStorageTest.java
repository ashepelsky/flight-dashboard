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
import com.ashep.flight.model.FlightInfoListDto;
import com.ashep.flight.model.FlightStatusEnum;
import com.ashep.flight.service.impl.FlightInMemoryStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

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

        final Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> given = getScheduleMap();

        storage.saveFlights(given);

        final Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> result = storage.getFlights();

        assertEquals("Maps for input and output should be the same object", given, result);

        FlightInfoListDto flightInfoList = result.get(DayOfWeek.MONDAY).get(DEPARTURE_TIME);

        FlightInfoDto flightInfoDto = flightInfoList.getInfoDtos().get(0);

        assertEquals(DEPARTURE_TIME, flightInfoDto.getDepartureTime());
        assertEquals(DESTINATION, flightInfoDto.getDestination());
        assertEquals(AIRPORT, flightInfoDto.getDestinationAirport());
        assertEquals("no 0", flightInfoDto.getFlightNo());
        assertEquals(FlightStatusEnum.DEPARTED, flightInfoDto.getStatus());

    }

    public static Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> getScheduleMap() {

        List<FlightInfoDto> list = new ArrayList<>();

        Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> map = new HashMap<>();

        SortedMap<LocalTime, FlightInfoListDto> sortedMap = new TreeMap<>();

        for (int i = 0; i < 5; i++) {
            final FlightInfoDto infoDto =
                    new FlightInfoDto(DEPARTURE_TIME.plusHours(i), DESTINATION, AIRPORT, "no " + i);

            FlightInfoListDto flightInfoList = new FlightInfoListDto(infoDto.getDepartureTime());
            flightInfoList.getInfoDtos().add(infoDto);

            sortedMap.put(infoDto.getDepartureTime(), flightInfoList);

        }

        // shuffle to check order in sorted set
        Collections.shuffle(list);

        map.put(DayOfWeek.MONDAY, sortedMap);

        return map;

    }

}
