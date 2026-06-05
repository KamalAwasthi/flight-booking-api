package com.ebay.flight_booking_api.service;

import com.ebay.flight_booking_api.exception.DuplicateFlightException;
import com.ebay.flight_booking_api.model.dto.request.CreateFlightRequest;
import com.ebay.flight_booking_api.model.dto.response.FlightResponse;
import com.ebay.flight_booking_api.model.entity.Flight;
import com.ebay.flight_booking_api.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightService {
    
    private final FlightRepository flightRepository;
    
    public FlightResponse createFlight(CreateFlightRequest request) {
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new DuplicateFlightException(request.getFlightNumber());
        }
        
        Flight flight = Flight.builder()
                .flightNumber(request.getFlightNumber())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .build();
        
        flightRepository.save(flight);
        
        return FlightResponse.builder()
                .flightNumber(flight.getFlightNumber())
                .totalSeats(flight.getTotalSeats())
                .availableSeats(flight.getAvailableSeats())
                .build();
    }
}
