package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.WaitItem

interface ResolveWaitingPort {
    fun resolve(): WaitItem
}