package io.nopecho.waiter.domain.event

import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.WaitingManager
import kotlinx.serialization.Serializable

@Serializable
data class CreatedWaitingMangerEvent(
    val managerId: String,
    val destination: String,
) : Event {
    companion object {
        fun from(waitingManager: WaitingManager): CreatedWaitingMangerEvent {
            return CreatedWaitingMangerEvent(
                managerId = waitingManager.id.value,
                destination = waitingManager.destination.url,
            )
        }
    }
}