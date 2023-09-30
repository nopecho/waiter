package io.nopecho.waiter.reactive.controller

import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException

@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    suspend fun httpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val message = ErrorMessage(e.message)
        val errors = ErrorResponse(400, listOf(message))

        return badRequest(errors)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    suspend fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessages = e.bindingResult
            .fieldErrors
            .associateBy({ it.field }, { it.defaultMessage ?: "unknown error message" })
            .map { ErrorMessage(it.value) }
        val errors = ErrorResponse(400, errorMessages)

        return badRequest(errors)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun webExchangeBindException(e: WebExchangeBindException): ResponseEntity<ErrorResponse> {
        val errorMessages = e.bindingResult
            .fieldErrors
            .associateBy({ it.field }, { it.defaultMessage ?: "unknown error message" })
            .map { ErrorMessage(it.value) }
        val errors = ErrorResponse(400, errorMessages)

        return badRequest(errors)
    }

    @ExceptionHandler(NoSuchElementException::class)
    suspend fun noSuchElementException(e: NoSuchElementException): ResponseEntity<ErrorResponse> {
        val message = ErrorMessage(e.message)
        val errors = ErrorResponse(404, listOf(message))

        return notFound(errors)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    suspend fun illegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val message = ErrorMessage(e.message)
        val errors = ErrorResponse(400, listOf(message))

        return badRequest(errors)
    }

    @ExceptionHandler(IllegalStateException::class)
    suspend fun illegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponse> {
        val message = ErrorMessage(e.message)
        val errors = ErrorResponse(400, listOf(message))

        return badRequest(errors)
    }

    @ExceptionHandler(ServerWebInputException::class)
    suspend fun serverWebInputException(e: ServerWebInputException): ResponseEntity<ErrorResponse> {
        val message = ErrorMessage(e.message)
        val errors = ErrorResponse(400, listOf(message))

        return badRequest(errors)
    }

    @ExceptionHandler(Exception::class)
    suspend fun exception(e: Exception): ResponseEntity<ErrorResponse> {
        e.printStackTrace()
        val message = ErrorMessage(e.message)
        val errors = ErrorResponse(500, listOf(message))

        return internalServerError(errors)
    }
}