#!/bin/sh

INFISICAL_TOKEN=$1
INFISICAL_ENV=$2
TOKEN_KEY=$3

# shellcheck disable=SC2046
echo `infisical export --token="$INFISICAL_TOKEN" --env="$INFISICAL_ENV" \
| sed "s/'//g" | grep -o "$TOKEN_KEY=[^ ]*" | cut -d'=' -f2` \
| docker login --username nopecho --password-stdin &&
make run