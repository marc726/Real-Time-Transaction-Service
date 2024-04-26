/**
 * Main entry point of the Transaction Service application.
 * Bootstraps the Spring Boot application and starts the service.
 */

package dev.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }
}