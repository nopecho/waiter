package io.nopecho.waiter.infra.reactive.redis.operations

import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class ReactiveSortedSet(
    private val template: ReactiveStringRedisTemplate
) {

    private val defaultDuration = Duration.ofMinutes(60 * 24)

    fun add(key: String, value: String, score: Double): Mono<Boolean> {
        return template.opsForZSet()
            .add(key, value, score)
            .flatMap {
                if (it) template.expire(key, defaultDuration)
                else Mono.just(false)
            }
    }

    fun size(key: String): Mono<Long> {
        return template.opsForZSet()
            .size(key)
    }

    fun popMin(key: String): Mono<String> {
        return template.opsForZSet()
            .popMin(key)
            .flatMap { Mono.just(it.value ?: "") }
    }
}