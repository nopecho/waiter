package io.nopecho.waiter.domain.event

import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.WaitingLine
import io.nopecho.waiter.domain.WaitingLineStatus
import kotlinx.serialization.Serializable

@Serializable
data class StatusChangedMangerEvent(
    val status: WaitingLineStatus,
    val waiting: Int,
    val destination: String = ""
) : Event {

    companion object {
        fun from(waitingLine: WaitingLine): StatusChangedMangerEvent {
            return StatusChangedMangerEvent(
                status = waitingLine.getStatus(),
                waiting = waitingLine.size(),
                destination = waitingLine.destination.url
            )
        }
    }
}