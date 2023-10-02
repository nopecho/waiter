package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.WaitingLine
import io.nopecho.waiter.domain.WaitingManager

interface LoadWaitingPort {
    suspend fun loadAll(waitingManager: WaitingManager): WaitingLine
    suspend fun count(waitingManager: WaitingManager): Long
}