package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.WaitingManager

interface UpdateManagerPort {
    suspend fun update(manger: WaitingManager): WaitingManager
    suspend fun updateAll(mangers: List<WaitingManager>): List<WaitingManager>
}