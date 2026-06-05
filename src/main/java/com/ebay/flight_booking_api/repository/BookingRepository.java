package com.ebay.flight_booking_api.repository;

import com.ebay.flight_booking_api.model.entity.Booking;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookingRepository {
    
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();
    
    public Booking save(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
        return booking;
    }
}
