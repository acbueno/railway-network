# Railway Network Simulator with Redis Cache

This project is a simple simulation of railway travel time between two cities. It demonstrates how to integrate **external APIs** and **Redis caching** to optimize route calculations.

The application uses:
- **Nominatim** to geocode city names into coordinates.
- **OpenRouteService** to calculate the real distance between coordinates.
- **Redis** to cache route distances and travel times, improving performance for repeated queries.

## Features

- Simulates travel time between two cities as if by train.
- Uses external APIs for geolocation and route calculation.
- Caches route results in Redis to reduce redundant API calls.
- Demonstrates practical use of caching in Spring Boot applications.

## Technologies Used

- Java 17+
- Spring Boot
- Redis
- Nominatim API
- OpenRouteService API
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Redis Server running locally or in Docker
- OpenRouteService API key

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/acbueno/railway-network.git
