package io.nopecho.waiter.`interface`.reactive.controller

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.RegisterWaitingCommand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class EventController(
    private val handlers: CommandHandlers
) {

    @GetMapping("/stream/events/waiting")
    fun apply(): Flow<ServerSentEvent<Any>> {
        return flow { serverSentEvent() }
    }

    private fun serverSentEvent(seq: Long): ServerSentEvent<Any> {
        if (seq == 3L) {
            throw IllegalArgumentException()
        }
        val command = RegisterWaitingCommand(
            destinationUrl = "https://naver.com"
        )
        return serverSentEvent(command)
    }
}