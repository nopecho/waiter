package io.nopecho.waiter.domain.repostory

import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.Waiting

interface WaitingRepository {
    fun add(waiting: Waiting): Waiting
    fun addAll(waitingList: List<Waiting>): List<Waiting>
    fun remove(waiting: Waiting): Waiting
    fun findAllBySourceDescScore(source: Source): List<Waiting>
    fun findAllBySourceDescStartedAt(source: Source): List<Waiting>
    fun countBySource(source: Source): Int
}