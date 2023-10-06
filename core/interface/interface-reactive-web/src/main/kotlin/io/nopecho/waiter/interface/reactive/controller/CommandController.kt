package io.nopecho.waiter.`interface`.reactive.controller

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.application.handlers.command.CreateWaitingMangerCommand
import io.nopecho.waiter.application.handlers.command.RegisterWaitingCommand
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.commons.utils.convertMap
import io.nopecho.waiter.`interface`.reactive.controller.model.ApplyRequestModel
import io.nopecho.waiter.`interface`.reactive.controller.model.WaitingMangerCreateRequestModel
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CommandController(
    private val handlers: CommandHandlers,
    @Value("\${waiter.client.url}")
    private val waiterUrl: String
) {

    @PostMapping("/apply")
    suspend fun apply(
        @Valid @RequestBody request: ApplyRequestModel
    ): ResponseEntity<Any> = coroutineScope {
        requireNotNull(request.destination)
        requireNotNull(request.destination.url)

        val command = RegisterWaitingCommand(
            destinationUrl = request.destination.url
        )

        val event = handlers.handle(command)

        val redirectionUrl = getRedirectionUrl(event)
        movedPermanently(redirectionUrl)
    }

    @PostMapping("/managers")
    suspend fun create(
        @Valid @RequestBody request: WaitingMangerCreateRequestModel
    ): ResponseEntity<Any> = coroutineScope {
        requireNotNull(request.destination)
        requireNotNull(request.destination.url)

        val command = CreateWaitingMangerCommand(
            destinationUrl = request.destination.url
        )

        val event = handlers.handle(command)
        created(event)
    }

    private fun getRedirectionUrl(event: Event): String {
        val eventMap = convertMap(event)
        val managerId = eventMap["managerId"] ?: ""
        return "$waiterUrl/$managerId"
    }
}