package com.example.transactionapi.model;

/**
 * Represents an incoming financial transaction request.
 * This class is automatically populated from JSON input.
 */
public class TransactionRequest {

    public String transactionId;
    public String accountId;
    public double amount;
    public String currency;
}