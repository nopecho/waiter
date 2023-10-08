package io.nopecho.waiter.commons.utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class UtilsTest {

    companion object {
        @JvmStatic
        fun queryParams(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("key", false),
                Arguments.of("key=", true),
                Arguments.of("key=value", true),
                Arguments.of("key=value?", true),
                Arguments.of("key=value&", false),
                Arguments.of("key=value&key2=", true),
                Arguments.of("key=value&key2=value2", true),
            )
        }
    }

    @ParameterizedTest(name = "query: {0} | isValid: {1}")
    @MethodSource("queryParams")
    fun isValidQueryParamsTest(query: String?, isValid: Boolean) {
        isValidQueryParams(query) shouldBe isValid
    }
}