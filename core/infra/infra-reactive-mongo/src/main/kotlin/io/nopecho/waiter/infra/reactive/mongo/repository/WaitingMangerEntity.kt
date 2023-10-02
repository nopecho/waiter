package io.nopecho.waiter.infra.reactive.mongo.repository

import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.Source
import io.nopecho.waiter.domain.WaitingBackpressure
import io.nopecho.waiter.domain.WaitingManager
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class WaitingMangerEntity(
    @Id
    val id: String,
    @Indexed(unique = true)
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
            id = ManagerId(id),
            source = source,
            backpressure = backpressure
        )
    }
}