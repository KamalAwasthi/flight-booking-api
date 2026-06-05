package com.ebay.flight_booking_api.exception;

public class NoSeatsAvailableException extends RuntimeException {
    
    public NoSeatsAvailableException(String message) {
        super(message);
    }
    
    public NoSeatsAvailableException(String flightNumber) {
        super("No seats available for flight: " + flightNumber);
    }
}
