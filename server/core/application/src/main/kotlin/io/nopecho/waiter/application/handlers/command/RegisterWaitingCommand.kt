package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.application.port.LoadManagerPort
import io.nopecho.waiter.application.port.RegisterWaitingPort
import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.Destination
import io.nopecho.waiter.domain.event.RegisteredWaitingEvent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AddWaitingCommandHandler(
    private val waitingFactory: WaitingFactory,
    private val loadMangerPort: LoadManagerPort,
    private val registerWaitingPort: RegisterWaitingPort,
) : CommandHandler {

    override fun canHandle(command: Command): Boolean {
        return command.isType(RegisterWaitingCommand::class.java)
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        val cmd = command as RegisterWaitingCommand
        val destination = Destination(cmd.destinationUrl)

        val manager = loadMangerPort.load(destination)
        val waiting = waitingFactory.create(manager, cmd)

        launch { registerWaitingPort.register(waiting) }
        RegisteredWaitingEvent.from(waiting)
    }
}

data class RegisterWaitingCommand(
    val destinationUrl: String,
) : Command