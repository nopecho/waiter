package io.nopecho.waiter.infra.reactive.redis.operations

import org.springframework.data.redis.core.ReactiveHashOperations
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
        return hash<V>().put(key, hashKey, hashValue)
            .flatMap {
                if (it) template.expire(key, duration)
                else Mono.just(false)
            }
    }

    fun getOrEmpty(key: String, hashKey: String): Mono<String> {
        return hash<String>().get(key, hashKey)
            .defaultIfEmpty("")
    }

    fun remove(key: String, hashKey: String) {
        hash<String>().remove(key, hashKey)
            .subscribe()
    }

    private fun <V> hash(): ReactiveHashOperations<String, String, V> {
        return template.opsForHash()
    }
}