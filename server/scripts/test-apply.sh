#!/bin/bash

url="http://localhost:9999/api/v1/waiting"

count=10000

function run_apply() {
    for ((i=1; i<=count; i++)); do
        response=$(curl -s -X POST "$url" \
            -H "Content-Type: application/json" \
            -d '{
                  "destination": {
                      "url": "http://localhost:9999/'$1'"
                  }
              }')

        echo "Response for index $i: $response"
    done
}

run_apply 1 &

# Wait for all background processes to finish
wait
echo "All processes done."