package com.ebay.flight_booking_api.api.controller;

import com.ebay.flight_booking_api.model.dto.request.CreateFlightRequest;
import com.ebay.flight_booking_api.model.dto.response.FlightResponse;
import com.ebay.flight_booking_api.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {
    
    private final FlightService flightService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse createFlight(@Valid @RequestBody CreateFlightRequest request) {
        return flightService.createFlight(request);
    }
}
