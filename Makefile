LOCAL_DC_FILE := $(CURDIR)/server/docker-compose-local.yml

TARGET ?=
NOW := $(shell date +"%Y%m%dT%H%M%S")
TAG ?= $(NOW)

SERVER_PREFIX ?= server
BOOTSTRAP_PREFIX ?= bootstrap
SERVER_PATH ?= $(CURDIR)/$(SERVER_PREFIX)
BUILD_PATH ?= $(CURDIR)/$(SERVER_PREFIX)/$(BOOTSTRAP_PREFIX)
DOCKER_REGISTRY ?= nopecho

.PHONY: build

up:
	@docker-compose -f $(LOCAL_DC_FILE) up -d

down:
	@docker-compose -f $(LOCAL_DC_FILE) down

build-server:
ifdef TARGET
	cd $(SERVER_PATH) && \
	$(SERVER_PATH)/gradlew clean :$(BOOTSTRAP_PREFIX):$(TARGET):build
else
	cd $(SERVER_PATH) && \
	$(SERVER_PATH)/gradlew clean build
endif

build-docker: build-server
ifdef TARGET
	cd $(BUILD_PATH)/$(TARGET) && \
	docker build \
	--build-arg APP_NAME=$(TARGET) \
	--tag $(TAG) \
	.
else
	echo "build module 'TARGET' must be not empty"
endif

run: build-docker
ifdef TARGET
	docker run \
	--env-file <(infisical export --token="$(INFISICAL_TOKEN)" --env="$(INFISICAL_ENV)" | sed "s/'//g") \
	$(TAG)
else
	echo "run 'TARGET' must be not empty"
endif

default: up