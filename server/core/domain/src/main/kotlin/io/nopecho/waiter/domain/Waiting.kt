package io.nopecho.waiter.domain

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Waiting(
    val id: String = UUID.randomUUID().toString(),
    val managerId: ManagerId,
    val destination: Destination = Destination(),
    val startedAt: Instant = now(),
) {
    fun startedAtToLong(): Long {
        return startedAt.toEpochMilliseconds()
    }
}

@Serializable
data class WaitingLine(
    val line: List<Waiting> = listOf(),
    val destination: Destination = Destination()
) {
    fun size(): Int {
        return line.size
    }

    fun getStatus(): WaitingLineStatus {
        return if (canResolve()) WaitingLineStatus.PASS
        else WaitingLineStatus.WAIT
    }

    fun register(destination: Destination): WaitingLine {
        return copy(destination = destination)
    }

    private fun canResolve(): Boolean {
        return size() <= 1
    }
}

enum class WaitingLineStatus {
    WAIT, PASS
}