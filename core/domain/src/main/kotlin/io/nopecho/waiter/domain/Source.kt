package io.nopecho.waiter.domain

import io.nopecho.waiter.commons.utils.enums.SourceType
import io.nopecho.waiter.commons.utils.isValidQueryParams
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val type: SourceType = SourceType.LINK,
    val from: SourceFrom = SourceFrom(),
    val to: SourceTo = SourceTo()
)

@Serializable
data class SourceFrom(
    val uri: HttpURI = HttpURI(),
)

@Serializable
data class SourceTo(
    val uri: HttpURI = HttpURI(),
)

@Serializable
data class HttpURI(
    val scheme: String = "http://",
    val host: String = "localhost",
    val path: String = "/",
    val query: String = "",
) {

    companion object {
        fun http(host: String, path: String, query: String = ""): HttpURI {
            return HttpURI(
                host = host,
                path = path,
                query = query
            )
        }

        fun https(host: String, path: String, query: String = ""): HttpURI {
            return HttpURI(
                scheme = "https://",
                host = host,
                path = path,
                query = query
            )
        }
    }

    fun getFullUri(): String {
        return when (isValidQueryParams(query)) {
            true -> "$scheme$host$path?$query"
            else -> "$scheme$host$path"
        }
    }
}