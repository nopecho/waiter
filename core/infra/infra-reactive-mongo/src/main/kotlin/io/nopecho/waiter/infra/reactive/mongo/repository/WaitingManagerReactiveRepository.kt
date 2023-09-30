package io.nopecho.waiter.infra.reactive.mongo.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface WaitingManagerReactiveRepository : ReactiveMongoRepository<WaitingMangerEntity, String>