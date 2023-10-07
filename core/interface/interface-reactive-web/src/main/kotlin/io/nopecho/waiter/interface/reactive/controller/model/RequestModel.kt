package io.nopecho.waiter.`interface`.reactive.controller.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.LocalDateTime

data class WaitingRegisterRequestModel(
    @field:NotNull
    val destination: DestinationRequestModel? = null
)

data class WaitingMangerCreateRequestModel(
    @field:NotNull
    val destination: DestinationRequestModel? = null,
    @field:NotNull
    val period: PeriodRequestModel? = null,
    @field:NotNull
    @field:Positive
    val throughput: Long? = null
)

data class DestinationRequestModel(
    @field:NotBlank
    val url: String? = null
)

data class PeriodRequestModel(
    @field:NotBlank
    val startDate: LocalDateTime? = null,
    @field:NotBlank
    val processingDate: LocalDateTime? = null,
    @field:NotBlank
    val endDate: LocalDateTime? = null
)