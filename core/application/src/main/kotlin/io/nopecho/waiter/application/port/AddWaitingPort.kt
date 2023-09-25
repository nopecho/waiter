package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.WaitItem

interface AddWaitingPort {
    fun add(item: WaitItem): Long
}