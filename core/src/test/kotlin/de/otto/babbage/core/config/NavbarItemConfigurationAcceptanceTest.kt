package de.otto.babbage.core.config

import de.otto.babbage.core.BaseAcceptanceTest
import io.kotest.matchers.shouldBe
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

class NavbarItemConfigurationAcceptanceTest : BaseAcceptanceTest() {

    @Test
    fun `should register navbar items`() {
        // when
        val document = Jsoup.connect("http://localhost:$port/actuator/status").get()

        // then
        val mainLinks = document.getElementsByClass("nav-link")
        mainLinks.map { it.text() } shouldBe listOf("Status", "Endpoints")

        val subLinks = document.getElementsByClass("dropdown-item")
        subLinks.map { it.text() } shouldBe listOf("Logger")
    }
}
