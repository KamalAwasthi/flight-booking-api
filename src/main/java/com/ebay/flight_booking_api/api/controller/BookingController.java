package com.ebay.flight_booking_api.api.controller;

import com.ebay.flight_booking_api.model.dto.request.BookTicketRequest;
import com.ebay.flight_booking_api.model.dto.response.BookingResponse;
import com.ebay.flight_booking_api.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    
    private final BookingService bookingService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse bookTicket(@Valid @RequestBody BookTicketRequest request) {
        return bookingService.bookTicket(request);
    }
}
