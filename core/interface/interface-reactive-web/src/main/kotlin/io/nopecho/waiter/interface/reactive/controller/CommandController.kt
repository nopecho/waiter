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
import org.springframework.http.HttpHeaders
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

        val redirection = getRedirectionPair(event)
        movedPermanently(
            location = redirection.first,
            headers = redirection.second
        )
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

    private fun getRedirectionPair(event: Event): Pair<String, HttpHeaders> {
        val eventMap = convertMap(event)
        val managerId = eventMap["managerId"] ?: ""
        val waitingId = eventMap["waitingId"]?.toString() ?: ""

        return Pair(
            first = "$waiterUrl/$managerId",
            second = getHeaders(waitingId)
        )
    }

    private fun getHeaders(waitingId: String): HttpHeaders {
        return HttpHeaders().apply {
            set("Set-Cookie", getCookieInfo(waitingId))
        }
    }

    private fun getCookieInfo(waitingId: String): String {
        return "$COOKIE_KEY=$waitingId; Path=/; Max-Age=3600; HttpOnly; Secure"
    }
}