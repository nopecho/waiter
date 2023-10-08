package io.nopecho.waiter.infra.reactive.redis.operations

import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class ReactiveHash<V : Any>(
    private val template: ReactiveStringRedisTemplate
) {

    fun put(
        key: String,
        hashKey: String,
        hashValue: V,
        duration: Duration = Duration.ofMinutes(60 * 24)
    ): Mono<Boolean> {
        return template.opsForHash<String, V>()
            .put(key, hashKey, hashValue)
            .flatMap {
                if (it) template.expire(key, duration)
                else Mono.just(false)
            }
    }

    fun getOrEmpty(key: String, hashKey: String): Mono<String> {
        return template.opsForHash<String, String>()
            .get(key, hashKey)
            .defaultIfEmpty("")
    }
}