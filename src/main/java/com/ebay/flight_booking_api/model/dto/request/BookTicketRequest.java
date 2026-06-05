package com.ebay.flight_booking_api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookTicketRequest {
    
    @NotBlank(message = "Flight number is required")
    private String flightNumber;
    
    @NotBlank(message = "Passenger name is required")
    private String passengerName;
}
