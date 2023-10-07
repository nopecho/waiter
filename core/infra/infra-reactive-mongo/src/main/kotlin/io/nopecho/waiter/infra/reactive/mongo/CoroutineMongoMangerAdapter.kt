package io.nopecho.waiter.infra.reactive.mongo

import io.nopecho.waiter.application.port.CreateManagerPort
import io.nopecho.waiter.application.port.LoadManagerPort
import io.nopecho.waiter.application.port.UpdateManagerPort
import io.nopecho.waiter.domain.Destination
import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.ManagerStatus
import io.nopecho.waiter.domain.WaitingManager
import io.nopecho.waiter.infra.reactive.mongo.repository.WaitingManagerReactiveRepository
import io.nopecho.waiter.infra.reactive.mongo.repository.WaitingMangerEntity
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class CoroutineMongoMangerAdapter(
    private val repository: WaitingManagerReactiveRepository
) : CreateManagerPort,
    UpdateManagerPort,
    LoadManagerPort {

    override suspend fun create(waitingManager: WaitingManager): WaitingManager {
        val entity = WaitingMangerEntity.from(waitingManager)

        return repository.insert(entity)
            .awaitSingle()
            .toDomain()
    }

    override suspend fun load(managerId: ManagerId): WaitingManager {
        return repository.findById(managerId.value)
            .awaitSingle()
            .toDomain()
    }

    override suspend fun load(destination: Destination): WaitingManager {
        return repository.findByDestination(destination)
            .awaitSingle()
            .toDomain()
    }

    override suspend fun loadAll(): List<WaitingManager> {
        return repository.findAll()
            .map { it.toDomain() }
            .collectList()
            .awaitSingle()
    }

    override suspend fun loadAllBy(page: Int, size: Int): List<WaitingManager> {
        val pageable: Pageable = PageRequest.of(page, size)

        return repository.findAllBy(pageable)
            .map { it.toDomain() }
            .collectList()
            .awaitSingle()
    }

    override suspend fun loadAllByStatus(status: ManagerStatus, page: Int, size: Int): List<WaitingManager> {
        val pageable: Pageable = PageRequest.of(page, size)

        return repository.findAllByStatus(status, pageable)
            .map { it.toDomain() }
            .collectList()
            .awaitSingle()
    }

    override suspend fun count(): Long {
        return repository.count()
            .awaitSingle()
    }

    override suspend fun update(manger: WaitingManager): WaitingManager {
        val entity = WaitingMangerEntity.from(manger)

        return repository.save(entity)
            .awaitSingle()
            .toDomain()
    }

    override suspend fun updateAll(mangers: List<WaitingManager>): List<WaitingManager> {
        val entities = mangers.map { WaitingMangerEntity.from(it) }

        return repository.saveAll(entities)
            .map { it.toDomain() }
            .collectList()
            .awaitSingle()
    }
}