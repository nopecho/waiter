package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.application.factory.WaitingFactory
import io.nopecho.waiter.application.port.AddWaitingPort
import io.nopecho.waiter.application.port.LoadWaitingManagerPort
import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.SourceUrl
import io.nopecho.waiter.domain.event.AddedWaitingEvent
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AddWaitingCommandHandler(
    private val factory: WaitingFactory,
    private val loadMangerPort: LoadWaitingManagerPort,
    private val addWaitingPort: AddWaitingPort,
) : CommandHandler {

    override fun canHandle(command: Command): Boolean {
        return command.isType(AddWaitingCommand::class.java)
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        val cmd = command as AddWaitingCommand
        val source = Source(
            from = SourceUrl(cmd.from),
            to = SourceUrl(cmd.to)
        )
        val manager = loadMangerPort.load(source)

        val waiting = factory.create(manager.source.from.url, manager.source.to.url)
        addWaitingPort.add(manager.id, waiting)

        AddedWaitingEvent.from(manager)
    }
}

data class AddWaitingCommand(
    val from: String,
    val to: String,
) : Command