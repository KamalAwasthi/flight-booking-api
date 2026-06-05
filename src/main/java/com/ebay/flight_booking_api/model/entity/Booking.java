package com.ebay.flight_booking_api.model.entity;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    String bookingId = UUID.randomUUID().toString();
    private String flightNumber;
    private String passengerName;
}
