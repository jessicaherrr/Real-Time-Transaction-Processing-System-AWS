# Real-Time-Transaction-Processing-System-AWS
This project simulates a simple financial transaction system using Java (Spring Boot) and AWS.

The goal was to redesign a slow synchronous API into a scalable, event-driven system, similar to what banks use for transaction processing.

## Architecture
Client → Spring Boot API → SQS → Lambda → DynamoDB → CloudWatch

## What I built
### 1. Java Spring Boot API
Created a REST endpoint: POST /transactions

Handles:
- request validation
- sending transaction data to SQS

### 2. Amazon SQS (Queue)
- Used SQS to buffer incoming transactions
- Decouples API from processing
- Helps handle high traffic spikes

### 3. AWS Lambda (Processing)
- Lambda is triggered automatically by SQS
- Processes each transaction
- Applies simple risk rules
- Example rule: amount > 10000 → HIGH_RISK

### 4. DynamoDB (Storage)
- Stores processed transactions
<img width="943" height="185" alt="image" src="https://github.com/user-attachments/assets/5cd1916b-deb4-4673-93ed-56a561eaaf63" />


### 5. CloudWatch (Logs)
- Logs each processed transaction
- Used for debugging and monitoring
-Example log: Processed tx-9002, risk=HIGH_RISK

## What I learned
- how to build an event-driven system on AWS
- how to decouple services using SQS
- how to debug distributed systems using CloudWatch
- how financial systems handle transactions at scale

