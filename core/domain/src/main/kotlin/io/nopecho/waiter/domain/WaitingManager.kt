package io.nopecho.waiter.domain

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class WaitingManager(
    val source: Source,
    val id: ManagerId = ManagerId(),
    val backpressure: WaitingBackpressure = WaitingBackpressure()
) {
    fun canResolve(size: Long): Boolean {
        return size <= backpressure.limit
    }
}

@Serializable
data class ManagerId(
    val value: String = UUID.randomUUID().toString()
)

