package io.nopecho.waiter.application.port

import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.Waiting

interface AddWaitingPort {
    suspend fun add(managerId: ManagerId, waiting: Waiting): Boolean
}