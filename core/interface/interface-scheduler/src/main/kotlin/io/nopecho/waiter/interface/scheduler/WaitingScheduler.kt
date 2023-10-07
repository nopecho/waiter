package io.nopecho.waiter.`interface`.scheduler

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.TakeWaitingCommand
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class WaitingScheduler(
    private val handlers: CommandHandlers,
) {
    @Async
    @Scheduled(fixedDelay = 111)
    fun takeSchedule() {
        val command = TakeWaitingCommand()
        runBlocking {
            handlers.handle(command)
        }
    }
}
