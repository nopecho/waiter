package io.nopecho.waiter.domain

import kotlinx.serialization.Serializable


@Serializable
data class Destination(
    val type: DestinationType = DestinationType.LINK,
    val source: Source,
)

@Serializable
data class Source(
    val uri: SimpleURI,
    val body: String? = null
)

@Serializable
data class SimpleURI(
    val scheme: String = "http://",
    val host: String,
    val path: String,
    val query: String = "",
) {
    companion object {
        fun http(host: String, path: String, query: String = ""): SimpleURI {
            return SimpleURI(
                host = host,
                path = path,
                query = query
            )
        }

        fun https(host: String, path: String, query: String = ""): SimpleURI {
            return SimpleURI(
                scheme = "https://",
                host = host,
                path = path,
                query = query
            )
        }
    }

    fun getFullURI(): String = "$scheme$host$path?$query"
}

enum class DestinationType {
    API,
    LINK
}