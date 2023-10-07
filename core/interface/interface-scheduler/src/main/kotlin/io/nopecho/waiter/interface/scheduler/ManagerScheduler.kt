package io.nopecho.waiter.`interface`.scheduler

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.ChangeStatusCommand
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ManagerScheduler(
    private val handlers: CommandHandlers,
) {
    @Async
    @Scheduled(fixedDelay = 1000)
    fun changeSchedule() {
        val now = LocalDateTime.now()

        val command = ChangeStatusCommand(now)
        runBlocking {
            handlers.handle(command)
        }
    }
}
