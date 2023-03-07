package de.otto.babbage.core.status

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import de.otto.babbage.core.status.GlobalModelAttributes
import io.mockk.MockKException
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
        val globalModelAttributes = GlobalModelAttributes(
            webEndpointProperties = webEndpointProperties,
            applicationName = "someApplicationName",
            basePath = "someBasePath"
        )
        every { webEndpointProperties.basePath } answers { "someManagementBasePath" }
        val model = ConcurrentModel()

        // when
        globalModelAttributes.addAttributes(model)

        // then
        model.size shouldBe 3
        model.getAttribute("managementBasePath") shouldBe "someManagementBasePath"
        model.getAttribute("basePath") shouldBe "someBasePath"
        model.getAttribute("applicationName") shouldBe "someApplicationName"
    }
}
