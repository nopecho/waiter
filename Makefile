LOCAL_DC_FILE := $(CURDIR)/docker/docker-compose.yml

up:
	@docker-compose -f $(LOCAL_DC_FILE) up -d

down:
	@docker-compose -f $(LOCAL_DC_FILE) down

default: up