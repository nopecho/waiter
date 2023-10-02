package io.nopecho.waiter.`interface`.reactive.controller

import org.springframework.http.ResponseEntity
import java.net.URI

internal fun ok(body: Any? = null): ResponseEntity<Any> {
    return ResponseEntity
        .ok(body)
}

internal fun created(body: Any? = null): ResponseEntity<Any> {
    return ResponseEntity
        .status(201)
        .body(body)
}

internal fun movedPermanently(to: String): ResponseEntity<Any> {
    val redirectUri = URI.create(to)
    return ResponseEntity
        .status(301)
        .location(redirectUri)
        .build()
}

internal fun badRequest(body: ErrorResponse? = null): ResponseEntity<ErrorResponse> {
    return ResponseEntity
        .badRequest()
        .body(body)
}

internal fun notFound(body: ErrorResponse? = null): ResponseEntity<ErrorResponse> {
    return ResponseEntity
        .status(body?.code ?: 404)
        .body(body)
}

internal fun internalServerError(body: ErrorResponse? = null): ResponseEntity<ErrorResponse> {
    return ResponseEntity
        .status(body?.code ?: 500)
        .body(body)
}

data class ErrorResponse(
    val code: Int = 500,
    val errors: List<ErrorMessage> = listOf()
)

data class ErrorMessage(
    val message: String?
)