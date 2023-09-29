package io.nopecho.waiter.domain

import kotlinx.serialization.Serializable

@Serializable
data class WaitingManager(
    val id: WaitingManagerId,
    val backpressure: WaitingBackpressure = WaitingBackpressure(),
    val waitingLine: WaitingLine = WaitingLine()
)

@Serializable
data class WaitingManagerId(
    val from: String = "from",
    val to: String = "to"
) {
    fun getValue(): String = "$from:$to"
}

@Serializable
data class WaitingLine(
    val queue: List<Waiting> = listOf()
)

