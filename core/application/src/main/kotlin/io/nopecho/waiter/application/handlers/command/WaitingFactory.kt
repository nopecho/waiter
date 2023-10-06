package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.domain.Destination
import io.nopecho.waiter.domain.Waiting
import io.nopecho.waiter.domain.WaitingManager
import org.springframework.stereotype.Component

@Component
class WaitingFactory {
    fun create(manager: WaitingManager, command: RegisterWaitingCommand): Waiting {
        return Waiting(
            managerId = manager.id,
            destination = Destination(command.destinationUrl)
        )
    }
}