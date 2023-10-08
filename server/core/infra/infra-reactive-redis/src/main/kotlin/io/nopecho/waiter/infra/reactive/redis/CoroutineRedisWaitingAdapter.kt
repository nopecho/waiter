package io.nopecho.waiter.infra.reactive.redis

import io.nopecho.waiter.application.port.LoadWaitingPort
import io.nopecho.waiter.application.port.RegisterWaitingPort
import io.nopecho.waiter.application.port.TakeWaitingPort
import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.Waiting
import io.nopecho.waiter.domain.WaitingLine
import io.nopecho.waiter.domain.WaitingManager
import io.nopecho.waiter.infra.reactive.redis.operations.ReactiveHash
import io.nopecho.waiter.infra.reactive.redis.operations.ReactiveSortedSet
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CoroutineRedisWaitingAdapter(
    private val sortedSet: ReactiveSortedSet,
    private val hashIndex: ReactiveHash<String>
) : LoadWaitingPort, RegisterWaitingPort, TakeWaitingPort {

    override suspend fun count(manager: WaitingManager): Long = coroutineScope {
        val key = getKey(manager)

        sortedSet.size(key).awaitSingle()
    }

    override suspend fun loadWaitingLineBy(managerId: ManagerId, waitingId: String): WaitingLine = coroutineScope {
        val indexingScore = hashIndex.getOrEmpty(
            key = getIndexKey(managerId),
            hashKey = waitingId
        ).awaitSingle()

        val key = getKey(managerId)
        val score = getOrDefaultIndexScore(indexingScore)

        val line = sortedSet.range(key, score)
            .map { convertWaiting(it) }
            .collectList()
            .awaitSingle()

        WaitingLine(line)
    }

    override suspend fun register(waiting: Waiting): Boolean = coroutineScope {
        val add = sortedSet.add(
            key = getKey(waiting),
            value = getValue(waiting),
            score = getScore(waiting)
        )

        val indexing = hashIndex.put(
            key = getIndexKey(waiting),
            hashKey = waiting.id,
            hashValue = waiting.startedAtToLong().toString()
        )

        Mono.zip(add, indexing)
            .map { it.t1 && it.t2 }
            .awaitSingle()
    }

    override suspend fun takeOrNull(manager: WaitingManager): Waiting? = coroutineScope {
        val key = getKey(manager)

        val value = sortedSet.popMinOrBlank(key).awaitSingle()

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
}