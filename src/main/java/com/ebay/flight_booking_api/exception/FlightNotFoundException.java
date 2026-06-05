package com.ebay.flight_booking_api.exception;

public class FlightNotFoundException extends RuntimeException {
    
    public FlightNotFoundException(String message) {
        super(message);
    }
    
    public FlightNotFoundException(String flightNumber) {
        super("Flight not found: " + flightNumber);
    }
}
