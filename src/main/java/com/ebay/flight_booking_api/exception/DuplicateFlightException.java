package com.ebay.flight_booking_api.exception;

public class DuplicateFlightException extends RuntimeException {
    
    public DuplicateFlightException(String flightNumber) {
        super("Flight already exists: " + flightNumber);
    }
}
