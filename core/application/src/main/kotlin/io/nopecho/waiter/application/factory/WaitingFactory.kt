package io.nopecho.waiter.application.factory

import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.SourceUrl
import io.nopecho.waiter.domain.Waiting
import org.springframework.stereotype.Component

@Component
class WaitingFactory {

    fun create(from: String, to: String): Waiting {
        return Waiting(
            source = Source(
                from = SourceUrl(from),
                to = SourceUrl(to)
            )
        )
    }
}