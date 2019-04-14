package com.ashep.flight.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import com.ashep.flight.model.FlightInfoDto;
import com.ashep.flight.model.FlightScheduleRow;
import com.ashep.flight.service.FlightManagerService;
import com.ashep.flight.service.FlightScheduleParserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController("/flight")
@Slf4j
public class FlightController {

    private final FlightManagerService flightManager;

    private final FlightScheduleParserService parserService;

    @Autowired
    public FlightController(final FlightManagerService flightManager,
                            final FlightScheduleParserService parserService) {

        this.flightManager = flightManager;
        this.parserService = parserService;
    }

    @GetMapping
    public Flux<List<FlightInfoDto>> test() {

        Flux<Long> interval = Flux.interval(Duration.ZERO, Duration.ofSeconds(10));

        Flux<List<FlightInfoDto>> stockTransactionFlux =
                Flux.fromStream(Stream.generate(flightManager::getClosestFlights));
        return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);
    }

    @PostMapping
    public ResponseEntity handleFileUpload(
            @RequestBody
                    String csv) {

        InputStream targetStream = new ByteArrayInputStream(csv.getBytes());

        final List<FlightScheduleRow> scheduleRows = parserService.parseFlightsSchedule(targetStream);
        flightManager.storeSchedule(scheduleRows);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity asd(Exception e) {

        @Data
        @AllArgsConstructor
        class ErrorResponse {

            private String error;
        }

        if (e instanceof IllegalArgumentException) {
            log.debug("Client error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

        log.debug("Unhandled server error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new ErrorResponse("Unhandled server error."));

    }

}
