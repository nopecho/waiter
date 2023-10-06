package io.nopecho.waiter.domain

import io.nopecho.waiter.commons.utils.NOT_VALID_URL
import io.nopecho.waiter.commons.utils.isValidUrl
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class WaitingManager(
    val destination: Destination,
    val id: ManagerId = ManagerId(),
    val backpressure: WaitingBackpressure = WaitingBackpressure()
) {
    fun isFull(size: Long): Boolean {
        return size > backpressure.limit
    }
}

@Serializable
data class ManagerId(
    val value: String = UUID.randomUUID().toString()
)

@Serializable
data class Destination(
    val url: String = "http://default.url"
) {
    init {
        require(isValidUrl(url)) { NOT_VALID_URL }
    }
}

