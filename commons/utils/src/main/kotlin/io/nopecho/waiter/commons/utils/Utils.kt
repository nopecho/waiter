package io.nopecho.waiter.commons.utils

fun isValidQueryParams(query: String?): Boolean {
    if (query.isNullOrBlank()) return true

    val regex = """^([^&=]+=[^&=]*)(?:&[^&=]+=[^&=]*)*$""".toRegex()

    return regex.matches(query)
}