package io.nopecho.waiter.`interface`.reactive.controller.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ApplyRequestModel(
    @field:NotNull
    val destination: DestinationRequestModel? = null
)

data class DestinationRequestModel(
    @field:NotBlank
    val url: String? = null
)

data class WaitingMangerCreateRequestModel(
    @field:NotNull
    val destination: DestinationRequestModel? = null
)