package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.domain.*
import org.springframework.stereotype.Component

@Component
class WaitingManagerFactory {
    fun create(command: CreateWaitingMangerCommand): WaitingManager {
        return WaitingManager(
            source = Source(
                from = SourceUrl(command.from),
                to = SourceUrl(command.to)
            ),
            backpressure = WaitingBackpressure(
                delay = ProcessingDelay(value = command.delay),
                throughput = command.throughput,
                limit = command.limit
            )
        )
    }
}