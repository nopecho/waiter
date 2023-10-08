package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.domain.Destination
import io.nopecho.waiter.domain.ManagerPeriod
import io.nopecho.waiter.domain.WaitingManager
import org.springframework.stereotype.Component

@Component
class WaitingManagerFactory {
    fun create(command: CreateMangerCommand): WaitingManager {
        return WaitingManager(
            destination = Destination(command.destinationUrl),
            throughput = command.throughput,
            period = ManagerPeriod(
                startDate = command.startDate,
                processingDate = command.processingDate,
                endDate = command.endDate
            )
        )
    }
}