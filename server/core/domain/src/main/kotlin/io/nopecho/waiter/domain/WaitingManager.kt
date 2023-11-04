package io.nopecho.waiter.domain

import io.nopecho.waiter.commons.utils.NOT_VALID_URL
import io.nopecho.waiter.commons.utils.infinityDate
import io.nopecho.waiter.commons.utils.isValidUrl
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

const val DEFAULT_THROUGHPUT = 100L
const val NOT_VALID_OPEN_AFTER_CLOSE = "종료일이 시작일 보다 이 전일 수 없습니다."
const val NOT_VALID_OPEN_AFTER_START = "처리일이 시작일 보다 이 전일 수 없습니다."

data class WaitingManager(
    val id: ManagerId = ManagerId(),
    val status: ManagerStatus = ManagerStatus.PENDING,
    val destination: Destination,
    val throughput: Long = DEFAULT_THROUGHPUT,
    val period: ManagerPeriod
) {
    fun changeStatus(date: LocalDateTime): WaitingManager {
        return when {
            period.isAfterCloseDate(date) -> with(ManagerStatus.DONE)

            period.isBetweenStartToClose(date) -> with(ManagerStatus.IN_PROGRESS)

            period.isBetweenOpenToStart(date) -> with(ManagerStatus.READY)

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
    val openDate: LocalDateTime,
    val startDate: LocalDateTime,
    val closeDate: LocalDateTime = infinityDate(),
) {
    init {
        require(closeDate.isAfter(openDate)) { NOT_VALID_OPEN_AFTER_CLOSE }
        require(startDate.isAfter(openDate)) { NOT_VALID_OPEN_AFTER_START }
    }

    fun isBetweenStartToClose(date: LocalDateTime): Boolean {
        return date.isAfter(startDate) && date.isBefore(closeDate)
    }

    fun isBetweenOpenToStart(date: LocalDateTime): Boolean {
        return date.isAfter(openDate) && date.isBefore(startDate)
    }

    fun isAfterCloseDate(date: LocalDateTime): Boolean {
        return date.isAfter(closeDate)
    }

    fun isBeforeOpenDate(date: LocalDateTime): Boolean {
        return date.isBefore(openDate)
    }
}

enum class ManagerStatus {
    PENDING, READY, IN_PROGRESS, DONE
}