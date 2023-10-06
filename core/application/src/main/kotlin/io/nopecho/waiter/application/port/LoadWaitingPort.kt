package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.Waiting
import io.nopecho.waiter.domain.WaitingLine
import io.nopecho.waiter.domain.WaitingManager

interface LoadWaitingPort {
    suspend fun loadAll(waiting: Waiting): WaitingLine
    suspend fun count(manager: WaitingManager): Long
}