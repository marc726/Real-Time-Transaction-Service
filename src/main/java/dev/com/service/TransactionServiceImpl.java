/**
 * Handles the business logic for authorizing transactions and loading funds.
 * Uses an in-memory map to store user balances and performs the necessary operations.
 */


 package dev.com.service;

import dev.com.models.*;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    private Map<String, Amount> userBalances = new HashMap<>();

    @Override
    public AuthorizationResponse authorize(AuthorizationRequest authorizationRequest) {
        String userId = authorizationRequest.getUserId();
        String messageId = authorizationRequest.getMessageId();
        Amount transactionAmount = authorizationRequest.getTransactionAmount();

        Amount currentBalance = userBalances.getOrDefault(userId, new Amount("0", transactionAmount.getCurrency(), DebitCredit.CREDIT));

        ResponseCode responseCode;
        Amount updatedBalance;

        if (isAuthorizationApproved(currentBalance, transactionAmount)) {
            updatedBalance = deductAmount(currentBalance, transactionAmount);
            responseCode = ResponseCode.APPROVED;
            userBalances.put(userId, updatedBalance);
        } else {
            updatedBalance = currentBalance;
            responseCode = ResponseCode.DECLINED;
        }

        return new AuthorizationResponse(userId, messageId, responseCode, updatedBalance);
    }

    @Override
    public LoadResponse load(LoadRequest loadRequest) {
        String userId = loadRequest.getUserId();
        String messageId = loadRequest.getMessageId();
        Amount transactionAmount = loadRequest.getTransactionAmount();

        Amount currentBalance = userBalances.getOrDefault(userId, new Amount("0", transactionAmount.getCurrency(), DebitCredit.CREDIT));
        Amount updatedBalance = addAmount(currentBalance, transactionAmount);

        userBalances.put(userId, updatedBalance);

        return new LoadResponse(userId, messageId, updatedBalance);
    }

    private boolean isAuthorizationApproved(Amount currentBalance, Amount transactionAmount) {
        BigDecimal currentBalanceAmount = new BigDecimal(currentBalance.getAmount());
        BigDecimal transactionAmountValue = new BigDecimal(transactionAmount.getAmount());

        return currentBalanceAmount.compareTo(transactionAmountValue) >= 0;
    }

    private Amount deductAmount(Amount balance, Amount amount) {
        BigDecimal balanceAmount = new BigDecimal(balance.getAmount());
        BigDecimal deductionAmount = new BigDecimal(amount.getAmount());

        BigDecimal updatedAmount = balanceAmount.subtract(deductionAmount);
        return new Amount(updatedAmount.toString(), balance.getCurrency(), DebitCredit.CREDIT);
    }

    private Amount addAmount(Amount balance, Amount amount) {
        BigDecimal balanceAmount = new BigDecimal(balance.getAmount());
        BigDecimal additionAmount = new BigDecimal(amount.getAmount());

        BigDecimal updatedAmount = balanceAmount.add(additionAmount);
        return new Amount(updatedAmount.toString(), balance.getCurrency(), DebitCredit.CREDIT);
    }
}