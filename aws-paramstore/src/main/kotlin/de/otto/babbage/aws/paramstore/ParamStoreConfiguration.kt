package de.otto.babbage.aws.paramstore

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.ssm.SsmClient

@Configuration
@EnableConfigurationProperties(ParamStoreProperties::class)
@ConditionalOnProperty(name = ["babbage.aws.paramstore.enabled"], havingValue = "true", matchIfMissing = true)
class ParamStoreConfiguration {

    @Bean
    fun paramStorePropertySourcePostProcessor(ssmClient: SsmClient): ParamStorePropertySourcePostProcessor {
        return ParamStorePropertySourcePostProcessor(ssmClient)
    }
}
