package io.nopecho.waiter.domain.event

import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.Waiting
import kotlinx.serialization.Serializable

@Serializable
data class ResolvedWaitingEvent(
    val waitingId: String,
    val destinationUrl: String,
) : Event {
    companion object {
        fun from(waiting: Waiting): ResolvedWaitingEvent {
            return ResolvedWaitingEvent(
                waitingId = waiting.id,
                destinationUrl = waiting.destination.url,
            )
        }
    }
}