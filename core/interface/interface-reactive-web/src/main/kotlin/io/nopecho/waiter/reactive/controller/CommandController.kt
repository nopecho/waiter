package io.nopecho.waiter.reactive.controller

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.CreateWaitingMangerCommand
import io.nopecho.waiter.reactive.controller.model.WaitingMangerCreateRequestModel
import io.nopecho.waiter.reactive.controller.model.WaitingRequestModel
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CommandController(
    private val handlers: CommandHandlers
) {

    @PostMapping("/apply")
    suspend fun apply(
        @Valid @RequestBody request: WaitingRequestModel
    ): ResponseEntity<Any> = coroutineScope {
        ok()
    }

    @PostMapping("/managers")
    suspend fun create(
        @Valid @RequestBody request: WaitingMangerCreateRequestModel
    ): ResponseEntity<Any> = coroutineScope {
        requireNotNull(request.source)
        requireNotNull(request.source.from)
        requireNotNull(request.source.to)

        val command = CreateWaitingMangerCommand(
            from = request.source.from,
            to = request.source.to
        )

        val event = handlers.handle(command)
        created(event)
    }
}