package io.nopecho.waiter.domain

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class WaitingManager(
    val source: Source,
    val id: WaitingManagerId = WaitingManagerId(),
    val backpressure: WaitingBackpressure = WaitingBackpressure(),
    val waitingLine: WaitingLine = WaitingLine()
)

@Serializable
data class WaitingManagerId(
    val value: String = UUID.randomUUID().toString()
)

@Serializable
data class WaitingLine(
    val line: List<Waiting> = listOf()
)