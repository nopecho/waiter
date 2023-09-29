package io.nopecho.waiter.domain

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Waiting(
    val source: Source,
    val score: Double = Double.MIN_VALUE,
    val startedAt: Instant = now(),
)