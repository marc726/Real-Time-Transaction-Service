/**
 * REST controller for handling transaction-related endpoints.
 * Exposes endpoints for authorizing transactions and loading funds.
 * Communicates with the TransactionService to process the requests.
 */

package dev.com.controller;

import dev.com.models.*;
import dev.com.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/authorization/{messageId}")
    public AuthorizationResponse authorize(@PathVariable String messageId, @RequestBody AuthorizationRequest authorizationRequest) {
        return transactionService.authorize(authorizationRequest);
    }

    @PutMapping("/load/{messageId}")
    public LoadResponse load(@PathVariable String messageId, @RequestBody LoadRequest loadRequest) {
        return transactionService.load(loadRequest);
    }
}