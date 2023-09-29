package io.nopecho.waiter.infra.reactive.redis.operations

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitFirstOrDefault
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component

@Component
class OpsSortedSet(
    private val template: ReactiveStringRedisTemplate
) {

    suspend fun add(
        key: String,
        value: String,
        score: Long
    ): Boolean = coroutineScope {
        template.opsForZSet()
            .add(key, value, score.toDouble())
            .awaitFirstOrDefault(false)
    }
}