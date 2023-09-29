package io.nopecho.waiter.domain

import kotlinx.serialization.Serializable
import java.util.concurrent.TimeUnit

const val DEFAULT_THROUGHPUT = 100L
const val DEFAULT_LIMIT = 1000L

@Serializable
data class WaitingBackpressure(
    val delay: ProcessingDelay = ProcessingDelay(),
    val throughput: Long = DEFAULT_THROUGHPUT,
    val limit: Long = DEFAULT_LIMIT,
)

@Serializable
data class ProcessingDelay(
    val timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
    val value: Long = 1000,
)