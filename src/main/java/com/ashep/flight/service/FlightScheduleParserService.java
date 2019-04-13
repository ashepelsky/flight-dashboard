package com.ashep.flight.service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightScheduleRow;

public interface FlightScheduleParserService {

    List<FlightScheduleRow> parseFlightsSchedule(InputStream inputStream);

}
