package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.Destination
import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.WaitingManager

interface LoadWaitingManagerPort {
    suspend fun load(managerId: ManagerId): WaitingManager
    suspend fun load(destination: Destination): WaitingManager
    suspend fun loadAll(): List<WaitingManager>
    suspend fun count(): Long
    suspend fun loadAllBy(page: Int, size: Int): List<WaitingManager>
}