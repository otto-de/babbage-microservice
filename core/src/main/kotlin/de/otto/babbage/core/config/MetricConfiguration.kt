package de.otto.babbage.core.config

import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetricConfiguration {

    @Bean
    fun clientRequestMetricsTagContributor(): ClientRequestMetricsTagContributor {
        return ClientRequestMetricsTagContributor()
    }

    @Bean
    fun meterRegistryCustomizer(): MeterRegistryCustomizer<MeterRegistry> =
        MeterRegistryCustomizer<MeterRegistry> { registry ->
            registry.config()
                .meterFilter(object : MeterFilter {
                    override fun configure(id: Meter.Id, config: DistributionStatisticConfig): DistributionStatisticConfig {
                        return if (id.name.startsWith("http.client")) {
                            config.merge(DistributionStatisticConfig.builder().percentilesHistogram(true).build())
                        } else {
                            config
                        }
                    }

                })
        }
}