/**
 * Unit tests for the TransactionController.
 * Verifies the behavior of the controller endpoints using mock objects and assertions.
 * @see TransactionController
 */


 package dev.com.controller;

import dev.com.models.*;
import dev.com.service.TransactionService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAuthorizeTransaction() throws Exception {
        String messageId = "msg456";
        AuthorizationRequest request = new AuthorizationRequest("user123", messageId, new Amount("100.00", "USD", DebitCredit.DEBIT));
        AuthorizationResponse response = new AuthorizationResponse("user123", messageId, ResponseCode.APPROVED, new Amount("900.00", "USD", DebitCredit.CREDIT));

        when(transactionService.authorize(any(AuthorizationRequest.class))).thenReturn(response);

        mockMvc.perform(put("/authorization/{messageId}", messageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.messageId").value(messageId))
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.APPROVED.toString()))
                .andExpect(jsonPath("$.balance.amount").value("900.00"));
    }

    @Test
    public void testLoadFunds() throws Exception {
        String messageId = "msg789";
        LoadRequest request = new LoadRequest("user123", messageId, new Amount("500.00", "USD", DebitCredit.CREDIT));
        LoadResponse response = new LoadResponse("user123", messageId, new Amount("1500.00", "USD", DebitCredit.CREDIT));

        when(transactionService.load(any(LoadRequest.class))).thenReturn(response);

        mockMvc.perform(put("/load/{messageId}", messageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.messageId").value(messageId))
                .andExpect(jsonPath("$.balance.amount").value("1500.00"));
    }
}