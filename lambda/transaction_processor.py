import json
import boto3
from decimal import Decimal
from datetime import datetime, timezone

dynamodb = boto3.resource("dynamodb")
table = dynamodb.Table("Transactions")


def classify_transaction(account_id, amount):
    if amount > Decimal("10000"):
        return "HIGH_RISK"
    return "NORMAL"


def lambda_handler(event, context):
    results = []

    for record in event["Records"]:
        body = json.loads(record["body"])

        transaction_id = body.get("transactionId")
        account_id = body.get("accountId")
        amount = Decimal(str(body.get("amount", 0)))
        currency = body.get("currency")

        risk_status = classify_transaction(account_id, amount)

        item = {
            "transactionId": transaction_id,
            "accountId": account_id,
            "amount": amount,
            "currency": currency,
            "riskStatus": risk_status,
            "processedAt": datetime.now(timezone.utc).isoformat()
        }

        table.put_item(Item=item)

        print(f"Processed {transaction_id}, risk={risk_status}")

        results.append({
            "transactionId": transaction_id,
            "accountId": account_id,
            "amount": str(amount),
            "currency": currency,
            "riskStatus": risk_status,
            "processedAt": item["processedAt"]
        })

    return {
        "statusCode": 200,
        "body": json.dumps(results)
    }