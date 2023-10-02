package io.nopecho.waiter.infra.reactive.mongo

import io.nopecho.waiter.application.port.CreateWaitingManagerPort
import io.nopecho.waiter.application.port.LoadWaitingManagerPort
import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.WaitingManager
import io.nopecho.waiter.infra.reactive.mongo.repository.WaitingManagerReactiveRepository
import io.nopecho.waiter.infra.reactive.mongo.repository.WaitingMangerEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class CoroutineMongoWaitingMangerAdapter(
    private val repository: WaitingManagerReactiveRepository
) : CreateWaitingManagerPort,
    LoadWaitingManagerPort {

    override suspend fun create(waitingManager: WaitingManager): WaitingManager = coroutineScope {
        val entity = WaitingMangerEntity.from(waitingManager)

        val saved = repository.insert(entity).awaitSingle()

        saved.toDomain()
    }

    override suspend fun load(managerId: ManagerId): WaitingManager = coroutineScope {
        val found = repository.findById(managerId.value).awaitSingle()

        found.toDomain()
    }

    override suspend fun load(source: Source): WaitingManager = coroutineScope {
        val found = repository.findBySource(source).awaitSingle()

        found.toDomain()
    }
}