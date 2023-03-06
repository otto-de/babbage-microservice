package de.otto.babbage.aws.paramstore

import de.babbage.aws.auth.AwsCredentialsConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.services.ssm.SsmClient

@Configuration
@Import(AwsCredentialsConfiguration::class)
class AwsSsmConfiguration {

    //TODO: Replace ssmClient with asyncSsmClient

    @Bean
    @ConditionalOnMissingBean
    fun ssmClient(awsCredentialsProvider: AwsCredentialsProvider): SsmClient {
        return SsmClient.builder()
            .credentialsProvider(awsCredentialsProvider)
            .build()
    }

}
