package io.nopecho.waiter.domain

import io.nopecho.waiter.commons.utils.NOT_VALID_URL
import io.nopecho.waiter.commons.utils.enums.SourceType
import io.nopecho.waiter.commons.utils.isUrl
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val type: SourceType = SourceType.LINK,
    val from: SourceUrl = SourceUrl(),
    val to: SourceUrl = SourceUrl()
)

@Serializable
data class SourceUrl(
    val url: String = "http://default.url"
) {
    init {
        require(isUrl(url)) { NOT_VALID_URL }
    }
}