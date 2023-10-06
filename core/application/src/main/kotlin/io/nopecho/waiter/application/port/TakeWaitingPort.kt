package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.Waiting
import io.nopecho.waiter.domain.WaitingManager

interface TakeWaitingPort {
    suspend fun takeOrNull(manager: WaitingManager): Waiting?
    suspend fun take(manager: WaitingManager): List<Waiting>
}