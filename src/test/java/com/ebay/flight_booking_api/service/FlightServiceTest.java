package com.ebay.flight_booking_api.service;

import com.ebay.flight_booking_api.exception.DuplicateFlightException;
import com.ebay.flight_booking_api.model.dto.request.CreateFlightRequest;
import com.ebay.flight_booking_api.model.dto.response.FlightResponse;
import com.ebay.flight_booking_api.model.entity.Flight;
import com.ebay.flight_booking_api.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
    
    @Mock
    private FlightRepository flightRepository;
    
    @InjectMocks
    private FlightService flightService;
    
    private CreateFlightRequest createFlightRequest;
    
    @BeforeEach
    void setUp() {
        createFlightRequest = CreateFlightRequest.builder()
                .flightNumber("AA123")
                .totalSeats(100)
                .build();
    }
    
    @Test
    void createFlight_Success() {
        when(flightRepository.existsByFlightNumber("AA123")).thenReturn(false);
        when(flightRepository.save(any(Flight.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        FlightResponse response = flightService.createFlight(createFlightRequest);
        
        assertNotNull(response);
        assertEquals("AA123", response.getFlightNumber());
        assertEquals(100, response.getTotalSeats());
        assertEquals(100, response.getAvailableSeats());
        
        verify(flightRepository, times(1)).existsByFlightNumber("AA123");
        verify(flightRepository, times(1)).save(any(Flight.class));
    }
    
    @Test
    void createFlight_DuplicateFlight_ThrowsException() {
        when(flightRepository.existsByFlightNumber("AA123")).thenReturn(true);
        
        DuplicateFlightException exception = assertThrows(
                DuplicateFlightException.class,
                () -> flightService.createFlight(createFlightRequest)
        );
        
        assertEquals("Flight already exists: AA123", exception.getMessage());
        verify(flightRepository, times(1)).existsByFlightNumber("AA123");
        verify(flightRepository, never()).save(any(Flight.class));
    }
}
