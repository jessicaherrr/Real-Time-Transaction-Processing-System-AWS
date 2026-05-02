import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 20,          // 20 users concurrently
  iterations: 200,  // 200 requests in total
};

export default function () {
  const payload = JSON.stringify({
    transactionId: `tx-${__VU}-${__ITER}`,
    accountId: `acc-${__VU}`,
    amount: Math.random() * 15000,
    currency: 'USD'
  });

  const res = http.post('http://localhost:8080/transactions', payload, {
    headers: { 'Content-Type': 'application/json' },
  });

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}