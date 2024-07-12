package de.otto.babbage.core.metrics

import io.micrometer.common.KeyValue
import io.micrometer.common.KeyValues
import org.springframework.boot.actuate.metrics.http.Outcome
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientRequestObservationContext
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention

class ClientRequestMetricsTagContributor : DefaultClientRequestObservationConvention() {

    override fun getLowCardinalityKeyValues(context: ClientRequestObservationContext): KeyValues {
        return KeyValues.of(
            KeyValue.of("method", context.request?.method()?.name() ?: "undefined"),
            KeyValue.of("uri", extractFirstPath(context.request)),
            KeyValue.of("client.name", context.request?.url()?.host ?: "none"),
            KeyValue.of("status", context.response?.statusCode()?.value()?.toString() ?: "undefined"),
            KeyValue.of("outcome", context.response?.statusCode()?.value()?.let { Outcome.forStatus(it).toString() } ?: Outcome.UNKNOWN.toString())
        )
    }

    private fun extractFirstPath(request: ClientRequest?): String {
        return if (request != null) {
            "/" + request.url().path.split("/").firstOrNull { it.isNotBlank() }
        } else {
            "undefined"
        }
    }

}
