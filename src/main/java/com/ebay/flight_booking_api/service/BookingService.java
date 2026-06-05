package com.ebay.flight_booking_api.service;

import com.ebay.flight_booking_api.exception.FlightNotFoundException;
import com.ebay.flight_booking_api.exception.NoSeatsAvailableException;
import com.ebay.flight_booking_api.model.dto.request.BookTicketRequest;
import com.ebay.flight_booking_api.model.dto.response.BookingResponse;
import com.ebay.flight_booking_api.model.entity.Booking;
import com.ebay.flight_booking_api.model.entity.Flight;
import com.ebay.flight_booking_api.repository.BookingRepository;
import com.ebay.flight_booking_api.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final ConcurrentHashMap<String, Object> flightLocks = new ConcurrentHashMap<>();
    
    public BookingResponse bookTicket(BookTicketRequest request) {
        String flightNumber = request.getFlightNumber();
        Object lock = flightLocks.computeIfAbsent(flightNumber, k -> new Object());
        
        synchronized (lock) {
            Flight flight = flightRepository.findByFlightNumber(flightNumber)
                    .orElseThrow(() -> new FlightNotFoundException(flightNumber));

            validateSeats(flightNumber, flight);
            
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            flightRepository.save(flight);
            
            Booking booking = createBooking(request);
            
            bookingRepository.save(booking);
            
            return mapToResponse(booking);
        }
    }

    private void validateSeats(String flightNumber, Flight flight) {
        if (flight.getAvailableSeats() <= 0) {
            throw new NoSeatsAvailableException(flightNumber);
        }
    }

    private Booking createBooking(BookTicketRequest request) {
        return Booking.builder()
                .bookingId(UUID.randomUUID().toString())
                .flightNumber(request.getFlightNumber())
                .passengerName(request.getPassengerName())
                .build();
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .flightNumber(booking.getFlightNumber())
                .passengerName(booking.getPassengerName())
                .build();
    }
}
