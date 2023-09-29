package io.nopecho.waiter.infra.reactive.mongo.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableReactiveMongoRepositories
@Configuration
class ReactiveMongoConfig