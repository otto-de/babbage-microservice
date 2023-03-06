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
            applicationName = "someApplicationName"
        )
        every { webEndpointProperties.basePath } answers { "someBasePath" }
        val model = ConcurrentModel()

        // when
        globalModelAttributes.addAttributes(model)

        // then
        model.getAttribute("webEndpointBasePath") shouldBe "someBasePath"
        model.getAttribute("applicationName") shouldBe "someApplicationName"
        model.size shouldBe 2
    }
}
