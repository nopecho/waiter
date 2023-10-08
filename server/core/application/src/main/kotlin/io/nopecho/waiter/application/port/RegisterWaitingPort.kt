package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.Waiting

interface RegisterWaitingPort {
    suspend fun register(waiting: Waiting): Boolean
}