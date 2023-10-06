package io.nopecho.waiter.domain.event

import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.WaitingLine
import io.nopecho.waiter.domain.WaitingManager
import kotlinx.serialization.Serializable

@Serializable
data class ResolvedWaitingEvent(
    val status: WaitingStatus,
    val waiting: Int,
    val destination: String = ""
) : Event {

    companion object {
        fun from(waitingLine: WaitingLine, manager: WaitingManager): ResolvedWaitingEvent {
            return if (waitingLine.canResolve()) {
                ResolvedWaitingEvent(
                    status = WaitingStatus.PASS,
                    waiting = waitingLine.size(),
                    destination = manager.destination.url
                )
            } else {
                ResolvedWaitingEvent(
                    status = WaitingStatus.WAIT,
                    waiting = waitingLine.size(),
                )
            }
        }
    }
}

enum class WaitingStatus {
    WAIT,
    PASS
}