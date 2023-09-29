package io.nopecho.waiter.reactive.controller.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class WaitingRequest(
    @field:NotNull
    val source: SourceRequest? = null
)

data class SourceRequest(
    @field:NotBlank
    val from: String? = null,
    @field:NotBlank
    val to: String? = null
)