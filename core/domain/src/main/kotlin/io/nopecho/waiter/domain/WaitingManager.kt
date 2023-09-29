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
    val from: From = From(),
    val to: To = To()
) {
    fun getValue(): String = "${from.value}:${to.value}"
}

@Serializable
data class WaitingLine(
    val line: List<Waiting> = listOf()
)

@Serializable
data class From(
    val value: String = "from"
)

@Serializable
data class To(
    val value: String = "to"
)