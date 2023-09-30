package io.nopecho.waiter.application.config

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.CreateWaitingMangerCommandHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommandHandlerConfig(
    private val createWaitingMangerCommandHandler: CreateWaitingMangerCommandHandler
) {
    @Bean
    fun commandHandlers(): CommandHandlers {
        val handlers = CommandHandlers()
        handlers.register(createWaitingMangerCommandHandler)
        return handlers
    }
}