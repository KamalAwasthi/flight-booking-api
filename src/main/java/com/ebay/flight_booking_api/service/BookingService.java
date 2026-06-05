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
            
            if (flight.getAvailableSeats() <= 0) {
                throw new NoSeatsAvailableException(flightNumber);
            }
            
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            flightRepository.save(flight);
            
            String bookingId = UUID.randomUUID().toString();
            Booking booking = Booking.builder()
                    .bookingId(bookingId)
                    .flightNumber(flightNumber)
                    .passengerName(request.getPassengerName())
                    .build();
            
            bookingRepository.save(booking);
            
            return BookingResponse.builder()
                    .bookingId(bookingId)
                    .flightNumber(flightNumber)
                    .passengerName(request.getPassengerName())
                    .build();
        }
    }
}
