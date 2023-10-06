package io.nopecho.waiter.`interface`.scheduler

import io.nopecho.waiter.application.handlers.ScheduleHandler
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class Scheduler(
    private val handler: ScheduleHandler,
) {
    @Scheduled(fixedDelay = 1)
    fun run() {
        runBlocking {
            handler.handle()
        }
    }
}
