package com.ebay.flight_booking_api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFlightRequest {
    
    @NotBlank(message = "Flight number is required")
    private String flightNumber;
    
    @Positive(message = "Total seats must be positive")
    private int totalSeats;
}
