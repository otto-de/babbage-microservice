package de.otto.babbage.validation

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ValidationAcceptanceTest : BaseAcceptanceTest() {

    @Test
    fun `should validate request parameter`() {
        //when
        val httpClient = HttpClient.newHttpClient()
        val response = httpClient.send(
            HttpRequest.newBuilder(URI.create("http://localhost:$port/test/?param=%3Cdiv%3E")).build(),
            HttpResponse.BodyHandlers.ofString()
        )

        //then
        response.body() shouldBe "test.param: Invalid id."
        response.statusCode() shouldBe 400
    }

}

@RestController
@Validated
class TestController {

    @GetMapping("/test/")
    fun test(@RequestParam @SafeId param: String) {
        println("Moin $param !!!")
    }

}
