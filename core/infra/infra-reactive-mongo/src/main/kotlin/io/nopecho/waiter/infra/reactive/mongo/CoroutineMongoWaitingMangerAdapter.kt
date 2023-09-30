package io.nopecho.waiter.infra.reactive.mongo

import io.nopecho.waiter.application.port.CreateWaitingManagerPort
import io.nopecho.waiter.domain.WaitingManager
import io.nopecho.waiter.infra.reactive.mongo.repository.WaitingManagerReactiveRepository
import io.nopecho.waiter.infra.reactive.mongo.repository.WaitingMangerEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

@Component
class CoroutineMongoWaitingMangerAdapter(
    private val reactiveRepository: WaitingManagerReactiveRepository
) : CreateWaitingManagerPort {

    override suspend fun create(waitingManager: WaitingManager): WaitingManager = coroutineScope {
        val entity = WaitingMangerEntity.from(waitingManager)

        val saved = (reactiveRepository.insert(entity).awaitSingleOrNull()
            ?: throw IllegalArgumentException())

        saved.toDomain()
    }
}