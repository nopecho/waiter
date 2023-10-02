package io.nopecho.waiter.infra.reactive.mongo.repository

import io.nopecho.waiter.domain.Source
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface WaitingManagerReactiveRepository : ReactiveMongoRepository<WaitingMangerEntity, String> {
    fun findBySource(source: Source): Mono<WaitingMangerEntity>
}