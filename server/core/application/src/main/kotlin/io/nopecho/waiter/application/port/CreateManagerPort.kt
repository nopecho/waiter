package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.WaitingManager

interface CreateManagerPort {
    suspend fun create(waitingManager: WaitingManager): WaitingManager
}