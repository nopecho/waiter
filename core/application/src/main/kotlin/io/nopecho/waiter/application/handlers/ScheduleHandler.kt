package io.nopecho.waiter.application.handlers

import io.nopecho.waiter.application.port.LoadWaitingManagerPort
import io.nopecho.waiter.application.port.TakeWaitingPort
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class ScheduleHandler(
    private val loadManagerPort: LoadWaitingManagerPort,
    private val takeWaitingPort: TakeWaitingPort,
) {
    private val size = 50

    suspend fun handle() = coroutineScope {
        val taskSize = getTaskSize()

        repeat(taskSize) {
            launch {
                loadManagerPort.loadAllBy(it, size).forEach {
                    launch { takeWaitingPort.take(it) }
                }
            }
        }
    }

    private suspend fun getTaskSize(): Int {
        val count = loadManagerPort.count()
        return getTotalPages(count, size)
    }

    private fun getTotalPages(totalCount: Long, pageSize: Int): Int {
        return ceil(totalCount.toDouble() / pageSize).toInt()
    }
}