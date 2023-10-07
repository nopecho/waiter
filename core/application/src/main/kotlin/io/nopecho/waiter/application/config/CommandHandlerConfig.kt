package io.nopecho.waiter.application.config

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommandHandlerConfig(
    private val takeWaitingHandler: TakeWaitingScheduleHandler,
    private val changeStatusMangerHandler: ChangeStatusMangerScheduleHandler,

    private val createWaitingMangerHandler: CreateWaitingMangerCommandHandler,
    private val addWaitingHandler: AddWaitingCommandHandler,
    private val resolveWaitingHandler: ResolveWaitingCommandHandler,
) {
    @Bean
    fun commandHandlers(): CommandHandlers {
        val handlers = CommandHandlers()
        handlers.register(takeWaitingHandler)
        handlers.register(changeStatusMangerHandler)
        
        handlers.register(createWaitingMangerHandler)
        handlers.register(addWaitingHandler)
        handlers.register(resolveWaitingHandler)

        return handlers
    }
}