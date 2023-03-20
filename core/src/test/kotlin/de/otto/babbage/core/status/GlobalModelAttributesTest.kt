package de.otto.babbage.core.status

import de.otto.babbage.core.management.GlobalModelAttributes
import de.otto.babbage.core.management.NavbarItem
import de.otto.babbage.core.management.NavbarItemType
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties
import org.springframework.ui.ConcurrentModel

@ExtendWith(MockKExtension::class)
class GlobalModelAttributesTest {

    @MockK
    private lateinit var webEndpointProperties: WebEndpointProperties

    @Test
    fun `should add global model attributes`() {
        // given
        val mainItem = NavbarItem(path = "/some/path", name = "Main Item", type = NavbarItemType.MAIN)
        val subItem = NavbarItem(path = "/some/path", name = "Sub Item", type = NavbarItemType.SUB)
        val globalModelAttributes = GlobalModelAttributes(
            webEndpointProperties = webEndpointProperties,
            applicationName = "someApplicationName",
            basePath = "someBasePath",
            navbarItems = listOf(mainItem, subItem)
        )
        every { webEndpointProperties.basePath } answers { "someManagementBasePath" }
        val model = ConcurrentModel()

        // when
        globalModelAttributes.addAttributes(model)

        // then
        model.size shouldBe 5
        model.getAttribute("managementBasePath") shouldBe "someManagementBasePath"
        model.getAttribute("basePath") shouldBe "someBasePath"
        model.getAttribute("applicationName") shouldBe "someApplicationName"
        model.getAttribute("mainNavbarItems") shouldBe listOf(mainItem)
        model.getAttribute("subNavbarItems") shouldBe listOf(subItem)
    }
}
