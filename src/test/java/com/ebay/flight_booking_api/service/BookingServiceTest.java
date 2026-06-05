package com.ebay.flight_booking_api.service;

import com.ebay.flight_booking_api.exception.FlightNotFoundException;
import com.ebay.flight_booking_api.exception.NoSeatsAvailableException;
import com.ebay.flight_booking_api.model.dto.request.BookTicketRequest;
import com.ebay.flight_booking_api.model.dto.response.BookingResponse;
import com.ebay.flight_booking_api.model.entity.Booking;
import com.ebay.flight_booking_api.model.entity.Flight;
import com.ebay.flight_booking_api.repository.BookingRepository;
import com.ebay.flight_booking_api.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    
    @Mock
    private FlightRepository flightRepository;
    
    @Mock
    private BookingRepository bookingRepository;
    
    @InjectMocks
    private BookingService bookingService;
    
    private BookTicketRequest bookTicketRequest;
    private Flight flight;
    
    @BeforeEach
    void setUp() {
        bookTicketRequest = BookTicketRequest.builder()
                .flightNumber("AA123")
                .passengerName("John Doe")
                .build();
        
        flight = Flight.builder()
                .flightNumber("AA123")
                .totalSeats(100)
                .availableSeats(50)
                .build();
    }
    
    @Test
    void bookTicket_Success() {
        when(flightRepository.findByFlightNumber("AA123")).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        BookingResponse response = bookingService.bookTicket(bookTicketRequest);
        
        assertNotNull(response);
        assertNotNull(response.getBookingId());
        assertEquals("AA123", response.getFlightNumber());
        assertEquals("John Doe", response.getPassengerName());
        assertEquals(49, flight.getAvailableSeats());
        
        verify(flightRepository, times(1)).findByFlightNumber("AA123");
        verify(flightRepository, times(1)).save(any(Flight.class));
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
    
    @Test
    void bookTicket_FlightNotFound_ThrowsException() {
        when(flightRepository.findByFlightNumber("AA123")).thenReturn(Optional.empty());
        
        FlightNotFoundException exception = assertThrows(
                FlightNotFoundException.class,
                () -> bookingService.bookTicket(bookTicketRequest)
        );
        
        assertEquals("Flight not found: AA123", exception.getMessage());
        verify(flightRepository, times(1)).findByFlightNumber("AA123");
        verify(flightRepository, never()).save(any(Flight.class));
        verify(bookingRepository, never()).save(any(Booking.class));
    }
    
    @Test
    void bookTicket_NoSeatsAvailable_ThrowsException() {
        flight.setAvailableSeats(0);
        when(flightRepository.findByFlightNumber("AA123")).thenReturn(Optional.of(flight));
        
        NoSeatsAvailableException exception = assertThrows(
                NoSeatsAvailableException.class,
                () -> bookingService.bookTicket(bookTicketRequest)
        );
        
        assertEquals("No seats available for flight: AA123", exception.getMessage());
        verify(flightRepository, times(1)).findByFlightNumber("AA123");
        verify(flightRepository, never()).save(any(Flight.class));
        verify(bookingRepository, never()).save(any(Booking.class));
    }
}
