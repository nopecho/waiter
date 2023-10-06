package io.nopecho.waiter.infra.reactive.mongo.repository

import io.nopecho.waiter.domain.Destination
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface WaitingManagerReactiveRepository : ReactiveMongoRepository<WaitingMangerEntity, String> {
    fun findByDestination(destination: Destination): Mono<WaitingMangerEntity>
    fun findAllBy(pageable: Pageable): Flux<WaitingMangerEntity>
}