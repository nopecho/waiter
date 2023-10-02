package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.application.port.LoadWaitingPort
import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.Event
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ResolveWaitingCommandHandler(
    private val loadWaitingPort: LoadWaitingPort,
) : CommandHandler {

    override fun canHandle(command: Command): Boolean {
        return command.isType(ResolveWaitingCommand::class.java)
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        TODO("Not yet implemented")
    }
}

data class ResolveWaitingCommand(
    val from: String,
    val to: String,
) : Command