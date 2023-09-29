package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.Waiting

interface ResolveWaitingPort {
    fun resolve(): Waiting
}