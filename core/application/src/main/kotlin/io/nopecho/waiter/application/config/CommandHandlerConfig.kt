package io.nopecho.waiter.application.config

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.AddWaitingCommandHandler
import io.nopecho.waiter.application.handlers.command.CreateWaitingMangerCommandHandler
import io.nopecho.waiter.application.handlers.command.ResolveWaitingCommandHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommandHandlerConfig(
    private val createWaitingMangerCommandHandler: CreateWaitingMangerCommandHandler,
    private val addWaitingCommandHandler: AddWaitingCommandHandler,
    private val resolveWaitingCommandHandler: ResolveWaitingCommandHandler
) {
    @Bean
    fun commandHandlers(): CommandHandlers {
        val handlers = CommandHandlers()
        handlers.register(createWaitingMangerCommandHandler)
        handlers.register(addWaitingCommandHandler)
        handlers.register(resolveWaitingCommandHandler)

        return handlers
    }
}