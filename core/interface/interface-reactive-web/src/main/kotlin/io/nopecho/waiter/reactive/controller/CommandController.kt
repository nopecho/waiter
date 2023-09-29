package io.nopecho.waiter.reactive.controller

import io.nopecho.waiter.reactive.controller.model.WaitingRequest
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CommandController {

    @PostMapping("/apply")
    suspend fun apply(
        @Valid @RequestBody request: WaitingRequest
    ): ResponseEntity<Any> = coroutineScope {
        ok()
    }
}