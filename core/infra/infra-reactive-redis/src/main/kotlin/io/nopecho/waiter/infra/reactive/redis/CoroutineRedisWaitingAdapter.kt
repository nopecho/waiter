package io.nopecho.waiter.infra.reactive.redis

import io.nopecho.waiter.application.port.LoadWaitingPort
import io.nopecho.waiter.application.port.RegisterWaitingPort
import io.nopecho.waiter.application.port.TakeWaitingPort
import io.nopecho.waiter.commons.utils.WAITING_MANAGER_PREFIX
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
    RegisterWaitingPort,
    TakeWaitingPort {

    override suspend fun loadAll(waiting: Waiting): WaitingLine = coroutineScope {
        TODO("Not yet implemented")
    }

    override suspend fun count(manager: WaitingManager): Long = coroutineScope {
        val key = getKey(manager)

        sortedSet.size(key)
            .awaitSingle()
    }

    override suspend fun register(waiting: Waiting): Boolean = coroutineScope {
        val key = getKey(waiting)
        val value = getValue(waiting)
        val score = getScore(waiting)

        sortedSet.add(key, value, score)
            .awaitSingle()
    }

    override suspend fun takeOrNull(manager: WaitingManager): Waiting? = coroutineScope {
        val key = getKey(manager)

        val value = sortedSet.popMinOrBlank(key)
            .awaitSingle()

        return@coroutineScope if (value.isNotBlank()) convertWaiting(value)
        else null
    }

    override suspend fun take(manager: WaitingManager): List<Waiting> = coroutineScope {
        val key = getKey(manager)
        val size = getTakeSize(manager)

        sortedSet.popMin(key, size)
            .map { convertWaiting(it) }
            .collectList()
            .awaitSingle()
    }

    private fun getKey(waiting: Waiting): String {
        return "$WAITING_MANAGER_PREFIX${waiting.managerId.value}"
    }

    private fun getKey(manager: WaitingManager): String {
        return "$WAITING_MANAGER_PREFIX${manager.id.value}"
    }

    private fun getTakeSize(manager: WaitingManager): Long {
        return manager.backpressure.throughput
    }

    private fun getValue(waiting: Waiting): String {
        return Json.encodeToString(waiting)
    }

    private fun getScore(waiting: Waiting): Double {
        return waiting.startedAt.toEpochMilliseconds().toDouble()
    }

    private fun convertWaiting(string: String): Waiting {
        return Json.decodeFromString(string)
    }
}