package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.application.port.CreateManagerPort
import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.DEFAULT_THROUGHPUT
import io.nopecho.waiter.domain.event.CreatedMangerEvent
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class CreateWaitingMangerCommandHandler(
    private val managerFactory: WaitingManagerFactory,
    private val createManagerPort: CreateManagerPort,
) : CommandHandler {
    override fun canHandle(command: Command): Boolean {
        return command.isType(CreateMangerCommand::class.java)
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        val cmd = command as CreateMangerCommand
        val manager = managerFactory.create(cmd)

        val created = createManagerPort.create(manager)

        CreatedMangerEvent.from(created)
    }
}

data class CreateMangerCommand(
    val destinationUrl: String,
    val throughput: Long = DEFAULT_THROUGHPUT,
    val startDate: LocalDateTime,
    val processingDate: LocalDateTime,
    val endDate: LocalDateTime
) : Command