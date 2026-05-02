# Day 3 Results - Transaction Processing Pipeline

## What I built
- Lambda triggered by SQS
- DynamoDB used to store processed transactions
- CloudWatch logs used for transaction processing visibility
- Rule-based financial risk classification

## Verified behavior
- tx-8001 -> HIGH_RISK
- tx-9002 -> HIGH_RISK

## Architecture
Spring Boot API -> SQS -> Lambda -> DynamoDB -> CloudWatch Logs

## Observations
- The system now processes transactions asynchronously.
- Lambda successfully classifies and stores transactions.
- Duplicate processing can occur in queue-based systems, so transaction idempotency should be added in a production design.