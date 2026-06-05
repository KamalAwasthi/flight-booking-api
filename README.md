# Flight Ticket Booking API

A Spring Boot 3 REST API for flight ticket booking with in-memory storage.

## Project Overview

Flight Ticket Booking API is a lightweight REST service that allows users to create flights and book tickets. The application uses in-memory storage with thread-safe operations to handle concurrent bookings without overbooking.

## Architecture

The application follows a layered architecture:

- **Controller Layer**: REST API endpoints (`FlightController`, `BookingController`)
- **Service Layer**: Business logic with thread-safe operations (`FlightService`, `BookingService`)
- **Repository Layer**: In-memory data access using `ConcurrentHashMap`
- **Model Layer**: Domain entities and DTOs
- **Exception Layer**: Custom exceptions with global exception handling

### Package Structure

```
com.ebay.flight_booking_api
├── api/controller          # REST controllers
├── service                # Business logic
├── repository             # In-memory data access
├── model
│   ├── entity            # Domain entities
│   └── dto               # Request/Response DTOs
└── exception             # Custom exceptions
```

## How to Run

### Prerequisites

- Java 21
- Maven 3.6+

### Build and Run

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

## Example Curl Requests

### Create a Flight

```bash
curl -X POST http://localhost:8080/api/flights \
  -H "Content-Type: application/json" \
  -d '{
    "flightNumber": "AA123",
    "totalSeats": 100
  }'
```

Response:
```json
{
  "flightNumber": "AA123",
  "totalSeats": 100,
  "availableSeats": 100
}
```

### Book a Ticket

```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "flightNumber": "AA123",
    "passengerName": "John Doe"
  }'
```

Response:
```json
{
  "bookingId": "550e8400-e29b-41d4-a716-446655440000",
  "flightNumber": "AA123",
  "passengerName": "John Doe"
}
```

## Assumptions

- Single JVM deployment (no distributed locking)
- In-memory storage only (data lost on restart)
- No user authentication required
- No payment processing
- Flight numbers are unique identifiers
- Each booking consumes exactly one seat

## Future Improvements

- Add database persistence (PostgreSQL, MySQL)
- Implement user authentication and authorization
- Add flight search functionality (by date, route, price)
- Implement cancellation and modification of bookings
- Add payment integration
- Implement distributed locking for multi-instance deployment
- Add caching layer for performance
- Implement pagination for flight listings
- Add comprehensive logging and monitoring
- Create Docker containerization
- Add API documentation with Swagger/OpenAPI
