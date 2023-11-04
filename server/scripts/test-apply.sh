#!/bin/bash

url="http://localhost:9999/api/v1/waiting"

count=100

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

function repeat_run_apply() {
    for (( i = 0; i < $1; i++ )); do
        run_apply 1 &
    done
}

repeat_run_apply 2000

# Wait for all background processes to finish
wait
echo "All processes done."