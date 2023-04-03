package de.otto.babbage.core.loggers

import de.otto.babbage.core.BaseAcceptanceTest
import io.kotest.matchers.shouldBe
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.logging.LoggersEndpoint

class LoggerControllerAcceptanceTest : BaseAcceptanceTest() {

    @Autowired
    private lateinit var loggersEndpoint: LoggersEndpoint

    @Test
    fun `should show all loggers with log levels`() {
        // given
        /**
         * needs warmup call because after the first call some further loggers are registered and the loggersEndpoint
         * returns more loggers.
         */
        Jsoup.connect("http://localhost:$port/actuator/logger").get()

        // when
        val document = Jsoup.connect("http://localhost:$port/actuator/logger").get()

        // then
        val loggerRows = document.getElementsByAttribute("data-logger-name")
        val missingLoggers =
            loggersEndpoint.loggers().loggers.keys - loggerRows.map { it.attr("data-logger-name") }.toSet()
        if (missingLoggers.isNotEmpty()) {
            fail("The following loggers are missing: ${missingLoggers.joinToString(",")}")
        }

        val loggerButtons = loggerRows[0].getElementsByTag("button")
        loggerButtons.map { it.text() } shouldBe listOf("OFF", "ERROR", "WARN", "INFO", "DEBUG", "TRACE")
    }
}