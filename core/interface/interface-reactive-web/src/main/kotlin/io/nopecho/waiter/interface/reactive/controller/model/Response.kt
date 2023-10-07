package io.nopecho.waiter.`interface`.reactive.controller.model

import org.springframework.http.HttpHeaders
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

internal fun movedPermanently(location: String, headers: HttpHeaders? = null): ResponseEntity<Any> {
    val redirectUri = URI.create(location)
    return ResponseEntity
        .status(301)
        .headers(headers)
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

const val COOKIE_KEY = "wa_id"
