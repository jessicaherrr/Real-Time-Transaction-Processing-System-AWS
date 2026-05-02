# Day 2 Results - API to SQS

## What I built
- Spring Boot API publishing transactions to Amazon SQS
- AWS SQS queue in us-east-1
- local AWS CLI configuration for development

## Test result
- API response:
  - status: ACCEPTED
  - mode: ASYNC
  - latency: ~402 ms on initial test

## Observations
- The API no longer performs full transaction processing inline.
- The request is accepted and forwarded to SQS.
- Queue verification showed older test messages first, so the queue should be purged before clean validation runs.
- Initial latency is still lower-complexity than the sync design, but includes remote AWS call overhead.