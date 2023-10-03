package io.nopecho.waiter.`interface`.reactive.controller

import kotlinx.datetime.Clock.System.now
import org.springframework.http.codec.ServerSentEvent
import java.time.Duration

val DEFAULT_RETRY: Duration = Duration.ofSeconds(3)

internal fun serverSentEvent(body: Any? = null): ServerSentEvent<Any> {
    return ServerSentEvent.builder<Any>()
        .id(now().toEpochMilliseconds().toString())
        .event(ServerSentEventType.CONNECT.name)
        .data(body)
        .retry(DEFAULT_RETRY)
        .build()
}

enum class ServerSentEventType {
    CONNECT,
    DISCONNECT
}