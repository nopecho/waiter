package io.nopecho.waiter.`interface`.reactive.controller

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.AddWaitingCommand
import io.nopecho.waiter.application.handlers.command.CreateWaitingMangerCommand
import io.nopecho.waiter.`interface`.reactive.controller.model.ApplyRequestModel
import io.nopecho.waiter.`interface`.reactive.controller.model.WaitingMangerCreateRequestModel
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
        @Valid @RequestBody request: ApplyRequestModel
    ): ResponseEntity<Any> = coroutineScope {
        requireNotNull(request.source)
        requireNotNull(request.source.from)
        requireNotNull(request.source.to)

        val command = AddWaitingCommand(
            from = request.source.from,
            to = request.source.to
        )

        val event = handlers.handle(command)
//        movedPermanently(command.to)
        ok(event)
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