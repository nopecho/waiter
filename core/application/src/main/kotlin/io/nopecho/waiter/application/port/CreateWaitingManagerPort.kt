package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.WaitingManager

interface CreateWaitingManagerPort {
    suspend fun create(waitingManager: WaitingManager): WaitingManager
}