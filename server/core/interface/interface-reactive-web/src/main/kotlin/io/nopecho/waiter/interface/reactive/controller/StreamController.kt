package io.nopecho.waiter.`interface`.reactive.controller

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.ResolveWaitingCommand
import kotlinx.coroutines.reactor.mono
import kotlinx.datetime.Clock
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.time.Duration

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
class StreamController(
    private val commandHandlers: CommandHandlers,
) {

    @GetMapping("/stream/events/waiting/{managerId}")
    fun apply(
        @PathVariable managerId: String,
        @RequestParam(defaultValue = "") waitingId: String,
    ): Flux<ServerSentEvent<Any>> {
        val command = ResolveWaitingCommand(waitingId, managerId)
        return Flux.interval(Duration.ofMillis(500)).flatMap {
            mono {
                val event = commandHandlers.handle(command)
                serverSentEvent(event)
            }
        }
    }

    private fun serverSentEvent(body: Any): ServerSentEvent<Any> {
        return ServerSentEvent.builder<Any>()
            .id(Clock.System.now().toEpochMilliseconds().toString())
            .data(body)
            .retry(Duration.ofMillis(3000))
            .build()
    }
}