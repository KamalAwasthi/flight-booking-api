package com.ebay.flight_booking_api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private String flightNumber;
    private int totalSeats;
    private int availableSeats;
}
