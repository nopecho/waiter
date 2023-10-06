#!/bin/bash

url="http://localhost:9999/api/v1/apply"

count=10000

function run_apply() {
    for ((i=1; i<=count; i++)); do
        response=$(curl -s -X POST "$url" \
            -H "Content-Type: application/json" \
            -d '{
                  "destination": {
                      "url": "http://localhost:10000/'$1'"
                  }
              }')

        echo "Response for index $i: $response"
    done
}

run_apply 1 &
run_apply 2 &
run_apply 3 &
run_apply 4 &
run_apply 5 &
run_apply 6 &
run_apply 7 &
run_apply 8 &
run_apply 9 &
run_apply 10 &

# Wait for all background processes to finish
wait
echo "All processes done."