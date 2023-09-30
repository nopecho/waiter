package io.nopecho.waiter.application.handlers

import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.commons.utils.NOT_FOUND_COMMAND_HANDLER
import kotlinx.coroutines.coroutineScope

class CommandHandlers(
    private var handlers: List<CommandHandler> = listOf()
) : CommandHandler {

    override fun canHandle(command: Command): Boolean {
        return handlers.any { it.canHandle(command) }
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        val handler = handlers.find { it.canHandle(command) }
            ?: throw NoSuchElementException(NOT_FOUND_COMMAND_HANDLER)

        handler.handle(command)
    }

    internal fun register(handler: CommandHandler) {
        handlers = handlers.plus(handler)
    }
}
