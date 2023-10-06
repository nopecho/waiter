package io.nopecho.waiter.domain

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Waiting(
    val managerId: ManagerId,
    val id: String = UUID.randomUUID().toString(),
    val destination: Destination = Destination(),
    val startedAt: Instant = now(),
) {
    fun startedAtToLong(): Long {
        return startedAt.toEpochMilliseconds()
    }
}

@Serializable
data class WaitingLine(
    val line: List<Waiting> = listOf()
) {
    fun size(): Int {
        return line.size
    }

    fun canResolve(): Boolean {
        return line.size <= 1
    }
}