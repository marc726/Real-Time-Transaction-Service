/**
 * Integration tests for the Transaction Service.
 * Verifies end-to-end functionality of the service by testing interactions between the controller and the service.
 * Uses the MockMvc framework to simulate HTTP requests (puts) and assertions on the responses.
 * @see TransactionController
 */

package dev.com;

import dev.com.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAuthorizeAndLoadFunds() throws Exception {
        String userId = "user123";
        String authorizeMessageId = "msg456";
        String loadMessageId = "msg789";

        // Load funds
        LoadRequest loadRequest = new LoadRequest(userId, loadMessageId, new Amount("500.00", "USD", DebitCredit.CREDIT));
        mockMvc.perform(put("/load/{messageId}", loadMessageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loadRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.messageId").value(loadMessageId))
                .andExpect(jsonPath("$.balance.amount").value("500.00"));

        // Authorize transaction
        AuthorizationRequest authorizationRequest = new AuthorizationRequest(userId, authorizeMessageId, new Amount("100.00", "USD", DebitCredit.DEBIT));
        mockMvc.perform(put("/authorization/{messageId}", authorizeMessageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorizationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.messageId").value(authorizeMessageId))
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.APPROVED.toString()))
                .andExpect(jsonPath("$.balance.amount").value("400.00"));

        // Authorize transaction after loading funds
        AuthorizationRequest authorizationRequest2 = new AuthorizationRequest(userId, "msg987", new Amount("300.00", "USD", DebitCredit.DEBIT));
        mockMvc.perform(put("/authorization/{messageId}", "msg987")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorizationRequest2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.messageId").value("msg987"))
                .andExpect(jsonPath("$.responseCode").value(ResponseCode.APPROVED.toString()))
                .andExpect(jsonPath("$.balance.amount").value("100.00"));
    }
}