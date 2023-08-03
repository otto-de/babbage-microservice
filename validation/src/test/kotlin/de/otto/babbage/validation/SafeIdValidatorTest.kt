package de.otto.babbage.validation

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

class SafeIdValidatorTest{
    private val validator = SafeIdValidator()

    @ParameterizedTest
    @MethodSource("data")
    fun testAllExampleIdValidPairs(id: String?, expected: Boolean) {
        validator.isValid(Objects.toString(id), null) shouldBe expected
    }

    companion object {
        @JvmStatic
        fun data(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of("id", true),
                Arguments.of("long-id", true),
                Arguments.of("long-id-with-numbers-1234567890", true),
                Arguments.of("long-id-with-numbers-1234567890-and-underscore_", true),
                Arguments.of("long-id-with-numbers-1234567890-and-underscore_-and-dot.", true),
                Arguments.of("ID-with-CAPITALS", true),
                Arguments.of(null, true),
                Arguments.of("id<", false),
                Arguments.of("id>", false),
                Arguments.of("id!", false),
                Arguments.of("id@", false),
                Arguments.of("id#", false),
                Arguments.of("id$", false),
                Arguments.of("id%", false),
                Arguments.of("id^", false),
                Arguments.of("id&", false),
                Arguments.of("id*", false),
                Arguments.of("id(", false),
                Arguments.of("id)", false),
                Arguments.of("id+", false),
                Arguments.of("id=", false)
            )
        }
    }
}
