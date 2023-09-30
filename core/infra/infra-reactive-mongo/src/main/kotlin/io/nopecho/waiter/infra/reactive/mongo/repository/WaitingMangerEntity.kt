package io.nopecho.waiter.infra.reactive.mongo.repository

import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.WaitingBackpressure
import io.nopecho.waiter.domain.WaitingManager
import io.nopecho.waiter.domain.WaitingManagerId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class WaitingMangerEntity(
    @Id
    val id: String,
    val source: Source,
    val backpressure: WaitingBackpressure,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        fun from(waitingManager: WaitingManager): WaitingMangerEntity {
            return WaitingMangerEntity(
                id = waitingManager.id.value,
                source = waitingManager.source,
                backpressure = waitingManager.backpressure
            )
        }
    }

    fun toDomain(): WaitingManager {
        return WaitingManager(
            id = WaitingManagerId(id),
            source = source,
            backpressure = backpressure
        )
    }
}