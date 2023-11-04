package io.nopecho.waiter.`interface`.reactive.controller

import io.nopecho.waiter.application.handlers.CommandHandlers
import io.nopecho.waiter.commons.contract.Event
import io.nopecho.waiter.commons.utils.convertMap
import io.nopecho.waiter.`interface`.reactive.controller.model.*
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
class CommandController(
    private val handlers: CommandHandlers,
    @Value("\${waiter.client.url}")
    private val waiterClientUrl: String
) {

    @PostMapping("/waiting")
    suspend fun apply(
        @Valid @RequestBody request: WaitingRegisterRequestModel
    ): ResponseEntity<Any> = coroutineScope {
        val command = request.toRegisterCommand()

        val event = handlers.handle(command)

        val redirection = getRedirectionUrl(event)
        redirectOk(redirection)
    }

    @PostMapping("/managers")
    suspend fun create(
        @Valid @RequestBody request: WaitingMangerCreateRequestModel
    ): ResponseEntity<Any> = coroutineScope {
        val command = request.toCreateCommand()

        val event = handlers.handle(command)
        created(event)
    }

    private fun getRedirectionUrl(event: Event): String {
        val eventMap = convertMap(event)
        val managerId = eventMap["managerId"]?.toString() ?: ""
        val waitingId = eventMap["waitingId"]?.toString() ?: ""

        return "$waiterClientUrl/waiting?mid=$managerId&wid=$waitingId"
    }
}