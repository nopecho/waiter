package io.nopecho.waiter.domain.event

import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.Waiting
import kotlinx.serialization.Serializable

@Serializable
data class RegisteredWaitingEvent(
    val managerId: String,
    val waitingId: String,
    val destination: String,
) : Event {
    companion object {
        fun from(waiting: Waiting): RegisteredWaitingEvent {
            return RegisteredWaitingEvent(
                managerId = waiting.managerId.value,
                waitingId = waiting.id,
                destination = waiting.destination.url,
            )
        }
    }
}