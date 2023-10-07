package io.nopecho.waiter.domain.event

import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.WaitingManager
import kotlinx.serialization.Serializable

@Serializable
data class CreatedMangerEvent(
    val managerId: String,
    val destination: String,
) : Event {
    companion object {
        fun from(waitingManager: WaitingManager): CreatedMangerEvent {
            return CreatedMangerEvent(
                managerId = waitingManager.id.value,
                destination = waitingManager.destination.url,
            )
        }
    }
}