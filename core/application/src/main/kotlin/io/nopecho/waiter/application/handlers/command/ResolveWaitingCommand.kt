package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.application.port.LoadManagerPort
import io.nopecho.waiter.application.port.LoadWaitingPort
import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.event.ResolvedWaitingEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class ResolveWaitingCommandHandler(
    private val loadWaitingPort: LoadWaitingPort,
    private val loadManagerPort: LoadManagerPort
) : CommandHandler {

    override fun canHandle(command: Command): Boolean {
        return command.isType(ResolveWaitingCommand::class.java)
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        val cmd = command as ResolveWaitingCommand
        val managerId = ManagerId(cmd.managerId)
        val waitingId = cmd.waitingId

        val asyncManger = async { loadManagerPort.load(managerId) }
        val asyncLine = async { loadWaitingPort.loadWaitingLineBy(managerId, waitingId) }

        val waitingLine = asyncLine.await()
            .register(asyncManger.await().destination)

        ResolvedWaitingEvent.from(waitingLine)
    }
}

data class ResolveWaitingCommand(
    val waitingId: String,
    val managerId: String,
) : Command