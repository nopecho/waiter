package io.nopecho.waiter.infra.reactive.redis

import io.nopecho.waiter.application.port.AddWaitingPort
import io.nopecho.waiter.application.port.LoadWaitingPort
import io.nopecho.waiter.commons.utils.WAITING_MANAGER_PREFIX
import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.Waiting
import io.nopecho.waiter.domain.WaitingLine
import io.nopecho.waiter.domain.WaitingManager
import io.nopecho.waiter.infra.reactive.redis.operations.ReactiveSortedSet
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Component
class CoroutineRedisWaitingAdapter(
    private val sortedSet: ReactiveSortedSet
) : LoadWaitingPort,
    AddWaitingPort {

    override suspend fun loadAll(waitingManager: WaitingManager): WaitingLine = coroutineScope {
        TODO("Not yet implemented")
    }

    override suspend fun count(waitingManager: WaitingManager): Long = coroutineScope {
        sortedSet.size(waitingManager.id.value).awaitSingle()
    }

    override suspend fun add(managerId: ManagerId, waiting: Waiting): Boolean = coroutineScope {
        val key = getKey(managerId)
        val value = getValue(waiting)

        sortedSet.add(key, value, waiting.score).awaitSingle()
    }

    private fun getKey(managerId: ManagerId) = "$WAITING_MANAGER_PREFIX${managerId.value}"
    private fun getValue(waiting: Waiting) = Json.encodeToString(waiting)
}