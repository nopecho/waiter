package io.nopecho.waiter.application.factory

import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.SourceUrl
import io.nopecho.waiter.domain.Waiting
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant
import org.springframework.stereotype.Component

@Component
class WaitingFactory {

    fun create(from: String, to: String): Waiting {
        val now = now()
        return Waiting(
            source = Source(
                from = SourceUrl(from),
                to = SourceUrl(to)
            ),
            score = timeScore(now),
            startedAt = now
        )
    }

    private fun timeScore(instant: Instant) = instant.toEpochMilliseconds().toDouble()
}