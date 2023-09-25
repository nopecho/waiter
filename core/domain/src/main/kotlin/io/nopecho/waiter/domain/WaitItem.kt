package io.nopecho.waiter.domain

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class WaitItem(
    val id: WaitId = WaitId(),
    val score: Long = 0,
    val destination: Destination,
    val from: From,
    val startedAt: Instant = now()
) {
    fun getWaitKey(): String = id.toWaitKey()
}

@Serializable
data class WaitId(
    val key: String = "key",
    val value: String = "value"
) {
    fun toWaitKey(): String = "$key:$value"
}

@Serializable
data class From(
    val uri: String,
)