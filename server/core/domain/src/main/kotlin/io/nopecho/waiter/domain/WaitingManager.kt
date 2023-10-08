package io.nopecho.waiter.domain

import io.nopecho.waiter.commons.utils.NOT_VALID_URL
import io.nopecho.waiter.commons.utils.infinityDate
import io.nopecho.waiter.commons.utils.isValidUrl
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

const val DEFAULT_THROUGHPUT = 100L
const val NOT_VALID_START_AFTER_END = "종료일이 시작일 보다 이 전일 수 없습니다."
const val NOT_VALID_START_AFTER_PROCESSING = "처리일이 시작일 보다 이 전일 수 없습니다."

data class WaitingManager(
    val id: ManagerId = ManagerId(),
    val status: ManagerStatus = ManagerStatus.PENDING,
    val destination: Destination,
    val throughput: Long = DEFAULT_THROUGHPUT,
    val period: ManagerPeriod
) {
    fun changeStatus(date: LocalDateTime): WaitingManager {
        return when {
            period.isAfterEndDate(date) -> with(ManagerStatus.DONE)

            period.isBetweenEndToProcessing(date) -> with(ManagerStatus.IN_PROGRESS)

            period.isBetweenProcessingToStart(date) -> with(ManagerStatus.READY)

            else -> with(ManagerStatus.PENDING)
        }
    }

    private fun with(status: ManagerStatus): WaitingManager {
        return copy(status = status)
    }
}

@Serializable
data class ManagerId(
    val value: String = UUID.randomUUID().toString()
)

@Serializable
data class Destination(
    val url: String = "http://not-yet.destination"
) {
    init {
        require(isValidUrl(url)) { NOT_VALID_URL }
    }
}

data class ManagerPeriod(
    val startDate: LocalDateTime,
    val processingDate: LocalDateTime,
    val endDate: LocalDateTime = infinityDate(),
) {
    init {
        require(endDate.isAfter(startDate)) { NOT_VALID_START_AFTER_END }
        require(processingDate.isAfter(startDate)) { NOT_VALID_START_AFTER_PROCESSING }
    }

    fun isBetweenEndToProcessing(date: LocalDateTime): Boolean {
        return date.isBefore(endDate) && date.isAfter(processingDate)
    }

    fun isBetweenProcessingToStart(date: LocalDateTime): Boolean {
        return date.isBefore(processingDate) && date.isAfter(startDate)
    }

    fun isAfterEndDate(date: LocalDateTime): Boolean {
        return date.isAfter(endDate)
    }

    fun isBeforeStartDate(date: LocalDateTime): Boolean {
        return date.isBefore(startDate)
    }
}

enum class ManagerStatus {
    PENDING, READY, IN_PROGRESS, DONE
}