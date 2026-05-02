# Day 1 Results - Synchronous Transaction API

## What I built
- Spring Boot API
- POST /transactions
- synchronous financial transaction processing
- simple risk rules
- local load testing with k6

## Risk rules
- amount > 10000 => HIGH_RISK
- currency != USD => INVALID_CURRENCY

## Test setup
- Localhost
- 20 virtual users
- 200 total requests

## Results
- Average latency: 471.91 ms
- Median latency: 469 ms
- p90 latency: 479.08 ms
- p95 latency: 503.15 ms
- Error rate: 0%
- Throughput: 42.26 requests/second

## Observations
- Each transaction takes about 0.47 seconds
- The API is blocking on validation, account check, risk check, and save simulation
- This design will become a bottleneck under heavier traffic
- This provides a baseline before redesigning the system into an asynchronous architecture on AWS.
