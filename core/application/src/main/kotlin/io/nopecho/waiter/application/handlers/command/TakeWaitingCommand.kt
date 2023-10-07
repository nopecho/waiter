package io.nopecho.waiter.application.handlers.command

import io.nopecho.waiter.application.port.LoadManagerPort
import io.nopecho.waiter.application.port.TakeWaitingPort
import io.nopecho.waiter.commons.contract.Command
import io.nopecho.waiter.commons.contract.CommandHandler
import io.nopecho.waiter.commons.contract.EmptyEvent
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.domain.ManagerStatus
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.ceil

@Service
@Transactional
class TakeWaitingScheduleHandler(
    private val loadManagerPort: LoadManagerPort,
    private val takeWaitingPort: TakeWaitingPort,
) : CommandHandler {

    override fun canHandle(command: Command): Boolean {
        return command.isType(TakeWaitingCommand::class.java)
    }

    override suspend fun handle(command: Command): Event = coroutineScope {
        command as TakeWaitingCommand

        val taskSize = getTaskSize(command.size)

        repeat(taskSize) {
            launch {
                loadManagerPort.loadAllByStatus(ManagerStatus.IN_PROGRESS, it, command.size).forEach {
                    launch {
                        takeWaitingPort.take(it)
                    }
                }
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

data class TakeWaitingCommand(
    val size: Int = 50
) : Command