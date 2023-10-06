package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.domain.Destination
import io.nopecho.waiter.domain.ProcessingDelay
import io.nopecho.waiter.domain.WaitingBackpressure
import io.nopecho.waiter.domain.WaitingManager
import org.springframework.stereotype.Component

@Component
class WaitingManagerFactory {
    fun create(command: CreateWaitingMangerCommand): WaitingManager {
        return WaitingManager(
            destination = Destination(command.destinationUrl),
            backpressure = WaitingBackpressure(
                delay = ProcessingDelay(value = command.delay),
                throughput = command.throughput,
                limit = command.limit
            )
        )
    }
}