package com.example.transactionapi.service;

import com.example.transactionapi.model.TransactionRequest;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

/**
 * Service responsible for publishing transaction events to AWS SQS.
 * This decouples the API layer from downstream processing.
 */
@Service
public class SqsPublisherService {

    private final SqsClient sqsClient;

    public SqsPublisherService() {
        this.sqsClient = SqsClient.create();
    }

    /**
     * Sends a transaction message to the SQS queue.
     */
    public void publishTransaction(String queueUrl, TransactionRequest request) {

        String messageBody = String.format(
                "{\"transactionId\":\"%s\",\"accountId\":\"%s\",\"amount\":%s,\"currency\":\"%s\"}",
                request.transactionId,
                request.accountId,
                request.amount,
                request.currency
        );

        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

        sqsClient.sendMessage(sendMessageRequest);
    }
}