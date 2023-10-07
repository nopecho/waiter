package io.nopecho.waiter.infra.reactive.redis

import io.nopecho.waiter.domain.ManagerId
import io.nopecho.waiter.domain.Waiting
import io.nopecho.waiter.domain.WaitingManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val SEPARATOR = "::"
const val INDEX_KEY_PREFIX = "INDEX$SEPARATOR"

const val WAITING_KEY_PREFIX = "WAITING$SEPARATOR"
const val INDEX_WAITING_KEY_PREFIX = "$INDEX_KEY_PREFIX$WAITING_KEY_PREFIX"

internal fun getKey(waiting: Waiting): String {
    return "$WAITING_KEY_PREFIX${waiting.managerId.value}"
}

internal fun getKey(manager: WaitingManager): String {
    return "$WAITING_KEY_PREFIX${manager.id.value}"
}

internal fun getKey(managerId: ManagerId): String {
    return "$WAITING_KEY_PREFIX${managerId.value}"
}

internal fun getIndexKey(waiting: Waiting): String {
    return "$INDEX_WAITING_KEY_PREFIX${waiting.managerId.value}"
}

internal fun getIndexKey(managerId: ManagerId): String {
    return "$INDEX_WAITING_KEY_PREFIX${managerId.value}"
}

internal fun getTakeSize(manager: WaitingManager): Long {
    return manager.throughput
}

internal fun getValue(waiting: Waiting): String {
    return Json.encodeToString(waiting)
}

internal fun getScore(waiting: Waiting): Double {
    return waiting.startedAtToLong().toDouble()
}

internal fun convertWaiting(string: String): Waiting {
    return Json.decodeFromString(string)
}

internal fun getOrDefaultIndexScore(value: String): Double {
    return if (value.isNotEmpty()) value.toDouble()
    else Double.MAX_VALUE
}