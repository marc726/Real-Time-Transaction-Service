/**
 * Unit tests for the TransactionServiceImpl.
 * Verifies the business logic and edge cases of the transaction service using mock objects and assertions.
 * @see TransactionServiceImpl
 */

package dev.com.service;

import dev.com.models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private Map<String, Amount> userBalances;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthorizeWithSufficientFunds() {
        String userId = "user123";
        Amount currentBalance = new Amount("1000.00", "USD", DebitCredit.CREDIT);
        AuthorizationRequest request = new AuthorizationRequest(userId, "msg456", new Amount("500.00", "USD", DebitCredit.DEBIT));

        when(userBalances.getOrDefault(eq(userId), any(Amount.class))).thenReturn(currentBalance);

        AuthorizationResponse response = transactionService.authorize(request);

        assertEquals(ResponseCode.APPROVED, response.getResponseCode());
        assertEquals("500.00", response.getBalance().getAmount());
        verify(userBalances).put(eq(userId), eq(new Amount("500.00", "USD", DebitCredit.CREDIT)));
    }

    @Test
    public void testAuthorizeWithInsufficientFunds() {
        String userId = "user123";
        Amount currentBalance = new Amount("100.00", "USD", DebitCredit.CREDIT);
        AuthorizationRequest request = new AuthorizationRequest(userId, "msg456", new Amount("500.00", "USD", DebitCredit.DEBIT));

        when(userBalances.getOrDefault(eq(userId), any(Amount.class))).thenReturn(currentBalance);

        AuthorizationResponse response = transactionService.authorize(request);

        assertEquals(ResponseCode.DECLINED, response.getResponseCode());
        assertEquals("100.00", response.getBalance().getAmount());
        verify(userBalances, never()).put(anyString(), any(Amount.class));
    }

    @Test
    public void testLoadFunds() {
        String userId = "user123";
        Amount currentBalance = new Amount("100.00", "USD", DebitCredit.CREDIT);
        LoadRequest request = new LoadRequest(userId, "msg789", new Amount("500.00", "USD", DebitCredit.CREDIT));

        when(userBalances.getOrDefault(eq(userId), any(Amount.class))).thenReturn(currentBalance);

        LoadResponse response = transactionService.load(request);

        assertEquals("600.00", response.getBalance().getAmount());
        verify(userBalances).put(eq(userId), eq(new Amount("600.00", "USD", DebitCredit.CREDIT)));
    }
}