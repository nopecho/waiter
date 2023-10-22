package io.nopecho.waiter.infra.reactive.mongo.repository

import io.nopecho.waiter.domain.*
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "waiting_mangers")
data class WaitingMangerEntity(
    @Id
    val id: String,
    val status: ManagerStatus,
    @Indexed(unique = true)
    val destination: Destination,
    val throughput: Long,
    val period: ManagerPeriod,

    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        fun from(waitingManager: WaitingManager): WaitingMangerEntity {
            return WaitingMangerEntity(
                id = waitingManager.id.value,
                status = waitingManager.status,
                destination = waitingManager.destination,
                throughput = waitingManager.throughput,
                period = waitingManager.period
            )
        }
    }

    fun toDomain(): WaitingManager {
        return WaitingManager(
            id = ManagerId(id),
            status = status,
            destination = destination,
            throughput = throughput,
            period = period
        )
    }
}