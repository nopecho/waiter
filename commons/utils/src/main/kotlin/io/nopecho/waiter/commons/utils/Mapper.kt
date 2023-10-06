package io.nopecho.waiter.commons.utils

import kotlin.reflect.full.memberProperties

fun <T : Any> convertMap(obj: T): Map<String, Any?> {
    return obj::class.memberProperties
        .associate { it.name to it.call(obj) }
}