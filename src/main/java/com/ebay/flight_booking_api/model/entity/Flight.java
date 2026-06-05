package com.ebay.flight_booking_api.model.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @NotBlank
    @Size(max = 20)
    private String flightNumber;
    private int totalSeats;
    private int availableSeats;
}
