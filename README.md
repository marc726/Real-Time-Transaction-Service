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