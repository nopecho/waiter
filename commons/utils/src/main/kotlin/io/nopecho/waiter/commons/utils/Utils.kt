package io.nopecho.waiter.commons.utils

import ru.lanwen.verbalregex.VerbalExpression

fun isValidQueryParams(query: String?): Boolean {
    if (query.isNullOrBlank()) return true

    val regex = """
        ^([^&=]+=[^&=]*)(?:&[^&=]+=[^&=]*)*$
        """.trimIndent().toRegex()

    return regex.matches(query)
}

fun isValidUrl(value: String): Boolean {
    val regex = VerbalExpression.regex()
        .startOfLine()
        .then("http").maybe("s")
        .then("://")
        .anythingBut(" ")
        .anythingBut("@%._+~#=")
        .endOfLine()
        .build()

    return regex.testExact(value)
}
