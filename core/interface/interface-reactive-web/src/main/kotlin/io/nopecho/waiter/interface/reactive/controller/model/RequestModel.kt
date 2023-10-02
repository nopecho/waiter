package io.nopecho.waiter.`interface`.reactive.controller.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ApplyRequestModel(
    @field:NotNull
    val source: SourceRequestModel? = null
)

data class SourceRequestModel(
    @field:NotBlank
    val from: String? = null,
    @field:NotBlank
    val to: String? = null
)

data class WaitingMangerCreateRequestModel(
    @field:NotNull
    val source: SourceRequestModel? = null
)