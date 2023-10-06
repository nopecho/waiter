#!/bin/bash

url="http://localhost:9999/api/v1/apply"

count=5000

for ((i=1; i<=count; i++)); do
    response=$(curl -s -X POST "$url" \
        -H "Content-Type: application/json" \
        -d '{
              "destination": {
                  "url": "https://m.trevari.co.kr/product?option='1'"
              }
          }')

    echo "Response for index $i: $response"
done
