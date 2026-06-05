package com.ebay.flight_booking_api.repository;

import com.ebay.flight_booking_api.model.entity.Flight;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FlightRepository {
    
    private final Map<String, Flight> flights = new ConcurrentHashMap<>();
    
    public Flight save(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
        return flight;
    }
    
    public Optional<Flight> findByFlightNumber(String flightNumber) {
        return Optional.ofNullable(flights.get(flightNumber));
    }
    
    public boolean existsByFlightNumber(String flightNumber) {
        return flights.containsKey(flightNumber);
    }
}
