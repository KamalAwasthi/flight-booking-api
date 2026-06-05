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
    
    public BookingResponse bookTicket(BookTicketRequest request) {
        synchronized (this) {
            Flight flight = flightRepository.findByFlightNumber(request.getFlightNumber())
                    .orElseThrow(() -> new FlightNotFoundException(request.getFlightNumber()));
            
            if (flight.getAvailableSeats() <= 0) {
                throw new NoSeatsAvailableException(request.getFlightNumber());
            }
            
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            flightRepository.save(flight);
            
            String bookingId = UUID.randomUUID().toString();
            Booking booking = Booking.builder()
                    .bookingId(bookingId)
                    .flightNumber(request.getFlightNumber())
                    .passengerName(request.getPassengerName())
                    .build();
            
            bookingRepository.save(booking);
            
            return BookingResponse.builder()
                    .bookingId(bookingId)
                    .flightNumber(request.getFlightNumber())
                    .passengerName(request.getPassengerName())
                    .build();
        }
    }
}
