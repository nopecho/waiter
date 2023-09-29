package io.nopecho.waiter.infra.reactive.redis

import io.nopecho.waiter.infra.reactive.redis.operations.OpsSortedSet
import org.springframework.stereotype.Component

@Component
class ReactiveRedisAdapter(
    private val sortedSet: OpsSortedSet
)