package io.nopecho.waiter.infra.reactive.redis

import io.nopecho.waiter.infra.reactive.redis.ops.OpsSortedSet
import org.springframework.stereotype.Component

@Component
class ReactiveRedisAdapter(
    private val sortedSet: OpsSortedSet
)