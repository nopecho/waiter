package io.nopecho.waiter.domain.repostory

import io.nopecho.waiter.domain.From
import io.nopecho.waiter.domain.To
import io.nopecho.waiter.domain.WaitingManager
import io.nopecho.waiter.domain.WaitingManagerId

interface WaitingManagerRepository {
    fun insert(waitingManager: WaitingManager): WaitingManager
    fun update(waitingManager: WaitingManager): WaitingManager
    fun delete(waitingManager: WaitingManager): WaitingManager
    fun findById(id: WaitingManagerId): WaitingManager
    fun findAll(): List<WaitingManager>
    fun findByFrom(from: From): WaitingManager
    fun findByTo(to: To): WaitingManager
}