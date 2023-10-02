package io.nopecho.waiter.domain.repostory

import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.WaitingManager

interface WaitingManagerRepository {
    fun insert(waitingManager: WaitingManager): WaitingManager
    fun update(waitingManager: WaitingManager): WaitingManager
    fun delete(waitingManager: WaitingManager): WaitingManager
    fun findById(id: ManagerId): WaitingManager
    fun findAll(): List<WaitingManager>
    fun findBySource(from: Source): WaitingManager
}