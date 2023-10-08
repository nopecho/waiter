package io.nopecho.waiter.domain.event

import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.WaitingLine
import io.nopecho.waiter.domain.WaitingLineStatus
import kotlinx.serialization.Serializable

@Serializable
data class ResolvedWaitingEvent(
    val status: WaitingLineStatus,
    val waitingOrder: Int,
    val destination: String = ""
) : Event {

    companion object {
        fun from(waitingLine: WaitingLine): ResolvedWaitingEvent {
            return ResolvedWaitingEvent(
                status = waitingLine.getStatus(),
                waitingOrder = waitingLine.size(),
                destination = waitingLine.destination.url
            )
        }
    }
}