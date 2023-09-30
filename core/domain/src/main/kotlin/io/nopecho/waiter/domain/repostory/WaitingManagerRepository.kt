package io.nopecho.waiter.domain.repostory

import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.WaitingManager
import io.nopecho.waiter.domain.WaitingManagerId

interface WaitingManagerRepository {
    fun insert(waitingManager: WaitingManager): WaitingManager
    fun update(waitingManager: WaitingManager): WaitingManager
    fun delete(waitingManager: WaitingManager): WaitingManager
    fun findById(id: WaitingManagerId): WaitingManager
    fun findAll(): List<WaitingManager>
    fun findBySource(from: Source): WaitingManager
}