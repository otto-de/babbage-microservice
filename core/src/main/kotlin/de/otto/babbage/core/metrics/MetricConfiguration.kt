package de.otto.babbage.core.metrics

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetricConfiguration {

    @Bean
    @ConditionalOnProperty("babbage.metrics.custom-html-client-metric.enabled", havingValue = "true")
    fun clientRequestMetricsTagContributor(): ClientRequestMetricsTagContributor {
        return ClientRequestMetricsTagContributor()
    }

}