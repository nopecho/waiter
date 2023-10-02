package io.nopecho.waiter.domain

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Waiting(
    val id: String = UUID.randomUUID().toString(),
    val source: Source,
    val startedAt: Instant = now(),
)

@Serializable
data class WaitingLine(
    val line: List<Waiting> = listOf()
)