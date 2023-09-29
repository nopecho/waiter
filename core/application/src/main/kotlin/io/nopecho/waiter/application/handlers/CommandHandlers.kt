package io.nopecho.waiter.application.handlers

import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.commons.utils.NOT_FOUND_COMMAND_HANDLER
import org.springframework.stereotype.Component

@Component
class CommandHandlers(
    private val handlers: List<CommandHandler> = listOf()
) : CommandHandler {

    override fun canHandle(command: Command): Boolean {
        return handlers.any { it.canHandle(command) }
    }

    override fun handle(command: Command): Event {
        val handler = handlers.find { it.canHandle(command) }
            ?: throw NoSuchElementException(NOT_FOUND_COMMAND_HANDLER)

        return handler.handle(command)
    }

    internal fun register(handler: CommandHandler) {
        handlers.plus(handler)
    }
}
