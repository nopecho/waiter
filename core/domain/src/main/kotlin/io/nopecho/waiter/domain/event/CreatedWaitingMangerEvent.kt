package io.nopecho.waiter.domain.event

import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.WaitingManager
import kotlinx.serialization.Serializable

@Serializable
data class CreatedWaitingMangerEvent(
    val waitingManagerId: String,
    val fromUrl: String,
    val toUrl: String,
) : Event {
    companion object {
        fun from(waitingManager: WaitingManager): CreatedWaitingMangerEvent {
            return CreatedWaitingMangerEvent(
                waitingManagerId = waitingManager.id.value,
                fromUrl = waitingManager.source.from.url,
                toUrl = waitingManager.source.to.url,
            )
        }
    }
}