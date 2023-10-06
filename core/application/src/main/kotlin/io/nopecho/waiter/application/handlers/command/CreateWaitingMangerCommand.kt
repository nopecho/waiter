package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.application.port.CreateWaitingManagerPort
import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.DEFAULT_LIMIT
import io.nopecho.waiter.domain.DEFAULT_THROUGHPUT
import io.nopecho.waiter.domain.event.CreatedWaitingMangerEvent
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateWaitingMangerCommandHandler(
    private val managerFactory: WaitingManagerFactory,
    private val createManagerPort: CreateWaitingManagerPort,
) : CommandHandler {
    override fun canHandle(command: Command): Boolean {
        return command.isType(CreateWaitingMangerCommand::class.java)
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        val cmd = command as CreateWaitingMangerCommand
        val manager = managerFactory.create(cmd)

        val created = createManagerPort.create(manager)

        CreatedWaitingMangerEvent.from(created)
    }
}

data class CreateWaitingMangerCommand(
    val destinationUrl: String,
    val limit: Long = DEFAULT_LIMIT,
    val throughput: Long = DEFAULT_THROUGHPUT,
    val delay: Long = 1000
) : Command