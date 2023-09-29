package io.nopecho.waiter.application.config

import io.nopecho.waiter.application.handlers.CommandHandlers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommandHandlerConfig {
    @Bean
    fun commandHandlers(): CommandHandlers {
        val handlers = CommandHandlers()

        return handlers
    }
}