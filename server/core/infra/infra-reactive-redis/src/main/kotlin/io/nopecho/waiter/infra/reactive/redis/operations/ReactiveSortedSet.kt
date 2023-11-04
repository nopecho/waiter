package io.nopecho.waiter.infra.reactive.redis.operations

import org.springframework.data.domain.Range
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.ReactiveZSetOperations
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class ReactiveSortedSet(
    private val template: ReactiveStringRedisTemplate
) {

    fun add(
        key: String,
        value: String,
        score: Double,
        duration: Duration = Duration.ofMinutes(60 * 24)
    ): Mono<Boolean> {
        return zSet().add(key, value, score)
            .flatMap {
                if (it) template.expire(key, duration)
                else Mono.just(false)
            }
    }

    fun size(key: String): Mono<Long> {
        return zSet().size(key)
    }

    fun popMinOrBlank(key: String): Mono<String> {
        return zSet().popMin(key)
            .map { it?.value ?: "" }
            .defaultIfEmpty("")
    }

    fun popMin(key: String, size: Long): Flux<String> {
        return zSet().popMin(key, size)
            .mapNotNull { it.value }
    }

    fun range(key: String, score: Double): Flux<String> {
        val rightRange = Range.Bound.inclusive(score)
        return zSet().rangeByScore(key, Range.leftUnbounded(rightRange))
    }

    private fun zSet(): ReactiveZSetOperations<String, String> {
        return template.opsForZSet()
    }
}