# Real-time Transaction Service

## Overview

This project is a real-time transaction service that calculates balances through event-sourcing. It is a critical component of a core banking engine, designed to handle loads (adding money to a user) and authorizations (conditionally removing money from a user).

## Technologies Used

- Java 8
- Spring Boot 2.5.0
- Maven 3.9.6
- JUnit 5
- Mockito
- IntelliJ IDEA (IDE)

## Features

- Accepts load and authorization transactions via REST API
- Returns updated balance after each transaction
- Saves authorization declines without impacting balance calculation
- Provides unit and integration tests for functionality verification

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.9.6 or higher

### Installation

1. Clone the repository:

`git clone https://github.com/marc726/real-time-transaction-service.git`

2. Navigate to the project directory:

`cd real-time-transaction-service`

3. Build the project using Maven:

`mvn clean install`

### Running the Application

1. Run the application using the following command:

`mvn spring-boot:run`

2. The application will start running on `http://localhost:8080`.

### Testing

To run the tests, use the following command:

`mvn test`

This will execute both unit tests and integration tests.

## API Documentation

The API documentation is available in the [service.yml](service.yml) file, which follows the OpenAPI 3.0 specification. It describes the endpoints, request/response formats, and error codes.

## Design Considerations

- **Spring Boot**: Spring Boot was chosen for its simplicity, autoconfiguration capabilities, and extensive ecosystem. It provides a solid foundation for building scalable and maintainable applications.

- **Layered Architecture**: The application follows a layered architecture, separating concerns into controller, service, and repository layers. This promotes code modularity, reusability, and easier testing.

- **Testing**: Comprehensive unit tests and integration tests were implemented to ensure the functionality of the transaction service. The tests cover various scenarios, including successful transactions, insufficient funds, and error handling.

- **In-Memory Data Storage**: For simplicity, an in-memory data storage approach was used to store user balances. However, the application can be easily extended to use a persistent database if required.

## Future Enhancements

- Implement a persistent database (e.g., MySQL, PostgreSQL) for storing transaction data and user balances.
- Add authentication and authorization mechanisms to secure the API endpoints.
- Integrate with a message queue system for asynchronous processing of transactions.
- Implement caching to improve performance and reduce database load.
- Enhance error handling and logging for better monitoring and debugging.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvement, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
