package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.application.port.LoadManagerPort
import io.nopecho.waiter.application.port.UpdateManagerPort
import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.EmptyEvent
import io.nopecho.waiter.commons.contract.Event
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.math.ceil

@Service
@Transactional
class ChangeStatusMangerScheduleHandler(
    private val loadManagerPort: LoadManagerPort,
    private val updateManagerPort: UpdateManagerPort,
) : CommandHandler {

    override fun canHandle(command: Command): Boolean {
        return command.isType(ChangeStatusCommand::class.java)
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        val (date, size) = command as ChangeStatusCommand
        val taskSize = getTaskSize(size)

        repeat(taskSize) {
            launch {
                val managers = loadManagerPort.loadAllBy(it, size).map { it.changeStatus(date) }

                updateManagerPort.updateAll(managers)
            }
        }

        EmptyEvent()
    }


    private suspend fun getTaskSize(size: Int): Int {
        val count = loadManagerPort.count()
        return getTotalPages(count, size)
    }

    private fun getTotalPages(totalCount: Long, pageSize: Int): Int {
        return ceil(totalCount.toDouble() / pageSize).toInt()
    }
}

data class ChangeStatusCommand(
    val date: LocalDateTime,
    val size: Int = 50
) : Command