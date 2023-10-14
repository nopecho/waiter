#!/bin/bash

url="http://localhost:9999/api/v1/managers"

count=10000

function run_create() {
    for ((i=1; i<=count; i++)); do
        response=$(curl -s -X POST "$url" \
            -H "Content-Type: application/json" \
            -d '{
                  "destination": {
                    "url": "http://localhost:9999/'$i'"
                  },
                  "period": {
                    "startDate": "2023-10-07T20:00:00",
                    "processingDate": "2023-10-07T20:05:00",
                    "endDate": "2023-10-07T20:10:00"
                  },
                  "throughput": 87
                }')

        echo "Response for index $i: $response"
    done
}

run_create 1 &

# Wait for all background processes to finish
wait
echo "All processes done."