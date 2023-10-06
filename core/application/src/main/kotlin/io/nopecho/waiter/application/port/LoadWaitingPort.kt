package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.WaitingLine
import io.nopecho.waiter.domain.WaitingManager

interface LoadWaitingPort {
    suspend fun count(manager: WaitingManager): Long
    suspend fun loadWaitingLineBy(managerId: ManagerId, waitingId: String): WaitingLine
}