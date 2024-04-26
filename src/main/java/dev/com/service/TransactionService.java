/**
 * Interface for the Transaction Service.
 * Defines the operations for authorizing transactions and loading funds.
 */

package dev.com.service;

import dev.com.models.*;

public interface TransactionService {
    AuthorizationResponse authorize(AuthorizationRequest authorizationRequest);
    LoadResponse load(LoadRequest loadRequest);
}