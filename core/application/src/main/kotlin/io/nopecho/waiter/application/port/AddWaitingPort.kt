package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.Waiting

interface AddWaitingPort {
    fun add(waiting: Waiting): Waiting
}