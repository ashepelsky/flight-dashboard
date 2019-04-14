package com.ashep.flight.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightInfoListDto;
import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.model.FlightStatusEnum;
import com.ashep.flight.service.impl.FlightManagerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.ashep.flight.service.FlightInMemoryStorageTest.getScheduleMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightManagerServiceTest {

    private static final String DESTINATION = "Destination";

    private static final String AIRPORT = "Airport";

    private static final String FLIGHT_NO = "No 123";

    private static final LocalTime DEPARTURE_TIME = LocalTime.of(12, 0);

    @InjectMocks
    private FlightManagerServiceImpl flightManagerService;

    @Mock
    private FlightStorage storage;

    @Test
    public void singeRowSuccessfullyStored() {

        final Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> resultMap =
                flightManagerService.storeSchedule(Collections.singletonList(getSimpleFlightScheduleRow()));

        assertNotNull("Schedule map can not be null", resultMap);
        assertFalse("Schedule map can not be empty", resultMap.isEmpty());

        SortedMap<LocalTime, FlightInfoListDto> resultSet = resultMap.get(DayOfWeek.MONDAY);

        assertNotNull("Schedule set can not be null", resultSet);

        FlightInfoListDto resultList = resultSet.entrySet()
                                                .stream()
                                                .findAny()
                                                .orElseThrow(() -> new RuntimeException("missing flight info"))
                                                .getValue();

        final FlightInfoDto result = resultList.getInfoDtos().get(0);

        assertNotNull("Schedule row can not be null", result);

        assertEquals(DEPARTURE_TIME, result.getDepartureTime());
        assertEquals(DESTINATION, result.getDestination());
        assertEquals(AIRPORT, result.getDestinationAirport());
        assertEquals(FLIGHT_NO, result.getFlightNo());
        assertEquals(FlightStatusEnum.DEPARTED, result.getStatus());

        verify(storage, times(1)).saveFlights(anyMap());

    }

    @Test
    public void multipleRowsSuccessfullyStored() {

        final Map<DayOfWeek, SortedMap<LocalTime, FlightInfoListDto>> resultMap =
                flightManagerService.storeSchedule(getScheduleList());

        assertNotNull("Schedule map can not be null", resultMap);
        assertFalse("Schedule map can not be empty", resultMap.isEmpty());

        SortedMap<LocalTime, FlightInfoListDto> resultSet = resultMap.get(DayOfWeek.MONDAY);

        assertEquals("Invalid count of rows", 5, resultSet.size());

        verify(storage, times(1)).saveFlights(anyMap());

    }

    @Test
    public void verifyGetFlightsFilteringAndSorting() {

        when(storage.getFlights()).thenReturn(getScheduleMap());

        final List<FlightInfoListDto> result = flightManagerService.getFlights(DEPARTURE_TIME.minusHours(1),
                                                                               DEPARTURE_TIME.plusHours(1)
                                                                                             .plusMinutes(1),
                                                                               DayOfWeek.MONDAY);

        assertNotNull("Schedule map can not be null", result);

        assertEquals("Two flights should be selected per time condition", 2, result.size());

        assertEquals("no 0", result.get(0).getInfoDtos().get(0).getFlightNo());
        assertEquals("no 1", result.get(1).getInfoDtos().get(0).getFlightNo());
    }

    private static FlightScheduleRow getSimpleFlightScheduleRow() {

        final FlightScheduleRow scheduleRow = new FlightScheduleRow();

        scheduleRow.setDays(Collections.singletonList(DayOfWeek.MONDAY));

        scheduleRow.setDepartureTime(DEPARTURE_TIME);
        scheduleRow.setDestination(DESTINATION);
        scheduleRow.setDestinationAirport(AIRPORT);
        scheduleRow.setFlightNo(FLIGHT_NO);

        return scheduleRow;
    }

    private static List<FlightScheduleRow> getScheduleList() {

        List<FlightScheduleRow> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            FlightScheduleRow row = getSimpleFlightScheduleRow();

            row.setFlightNo("no " + i);
            row.setDepartureTime(DEPARTURE_TIME.plusHours(i));
            list.add(row);
        }

        // shuffle to check order in sorted set
        Collections.shuffle(list);

        return list;

    }

}
