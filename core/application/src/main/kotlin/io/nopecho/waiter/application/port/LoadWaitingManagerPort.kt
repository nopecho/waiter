package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.WaitingManager

interface LoadWaitingManagerPort {
    suspend fun load(managerId: ManagerId): WaitingManager
    suspend fun load(source: Source): WaitingManager
}