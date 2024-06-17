package de.otto.babbage.core.config

import io.kotest.matchers.collections.shouldContain
import io.micrometer.common.KeyValue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientRequestObservationContext
import org.springframework.web.reactive.function.client.ClientResponse
import java.net.URI

internal class ClientRequestMetricsTagContributorTest {

    @Test
    fun `should create key value pairs for web client request`() {
        // given
        val url = "http://www.otto.de/first/second/third"
        val clientRequest = ClientRequest.create(HttpMethod.GET, URI.create(url))
        val clientResponse = ClientResponse.create(HttpStatusCode.valueOf(200)).build()
        val observationContext = ClientRequestObservationContext(clientRequest)
        observationContext.setResponse(clientResponse)

        // when
        val result = ClientRequestMetricsTagContributor().getLowCardinalityKeyValues(observationContext)

        // then
        result shouldContain KeyValue.of("method", "GET")
        result shouldContain KeyValue.of("uri", "/first")
        result shouldContain KeyValue.of("client.name", "www.otto.de")
        result shouldContain KeyValue.of("status", "200")
        result shouldContain KeyValue.of("outcome", "SUCCESS")
    }

    @Test
    fun `should create fallback key value pairs for empty request`() {
        // given
        val mockedContext = Mockito.mock(ClientRequestObservationContext::class.java)

        // when
        val result = ClientRequestMetricsTagContributor().getLowCardinalityKeyValues(mockedContext)

        // then
        result shouldContain KeyValue.of("method", "undefined")
        result shouldContain KeyValue.of("uri", "undefined")
        result shouldContain KeyValue.of("client.name", "none")
        result shouldContain KeyValue.of("status", "undefined")
        result shouldContain KeyValue.of("outcome", "UNKNOWN")
    }
}