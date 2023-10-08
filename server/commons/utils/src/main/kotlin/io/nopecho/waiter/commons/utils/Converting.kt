package io.nopecho.waiter.commons.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlin.reflect.full.memberProperties

fun <T : Any> convertMap(obj: T): Map<String, Any?> {
    return obj::class.memberProperties
        .associate { it.name to it.call(obj) }
}

fun convertLocalDate(date: LocalDateTime): java.time.LocalDateTime = date.toJavaLocalDateTime()

fun convertLocalDate(date: java.time.LocalDateTime): LocalDateTime = date.toKotlinLocalDateTime()