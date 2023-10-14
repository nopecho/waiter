LOCAL_DC_FILE := $(CURDIR)/server/docker-compose-local.yml

up:
	@docker-compose -f $(LOCAL_DC_FILE) up -d

down:
	@docker-compose -f $(LOCAL_DC_FILE) down

default: up