package com.example.transactionapi.controller;

import com.example.transactionapi.model.TransactionRequest;
import com.example.transactionapi.service.SqsPublisherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for handling incoming transaction requests.
 * This API now follows an asynchronous design by sending data to SQS.
 */
@RestController
public class TransactionController {

    private final SqsPublisherService sqsPublisherService;

    @Value("${app.sqs.queue-url}")
    private String queueUrl;

    public TransactionController(SqsPublisherService sqsPublisherService) {
        this.sqsPublisherService = sqsPublisherService;
    }

    /**
     * Accepts a transaction request and forwards it to SQS.
     * Returns immediately without performing heavy processing.
     */
    @PostMapping("/transactions")
    public Map<String, Object> processTransaction(@RequestBody TransactionRequest request) {

        long start = System.currentTimeMillis();

        // Basic validation
        if (request.transactionId == null || request.transactionId.isBlank()) {
            throw new IllegalArgumentException("transactionId is required");
        }
        if (request.accountId == null || request.accountId.isBlank()) {
            throw new IllegalArgumentException("accountId is required");
        }
        if (request.amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than 0");
        }
        if (request.currency == null || request.currency.isBlank()) {
            throw new IllegalArgumentException("currency is required");
        }

        // Send transaction to SQS (asynchronous processing)
        sqsPublisherService.publishTransaction(queueUrl, request);

        long latency = System.currentTimeMillis() - start;

        System.out.println("Accepted transaction " + request.transactionId
                + " account=" + request.accountId
                + " amount=" + request.amount
                + " latencyMs=" + latency);

        Map<String, Object> response = new HashMap<>();
        response.put("transactionId", request.transactionId);
        response.put("status", "ACCEPTED");
        response.put("mode", "ASYNC");
        response.put("latencyMs", latency);

        return response;
    }
}