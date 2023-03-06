package de.babbage.aws.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider

@Configuration
class AwsCredentialsConfiguration {

    @Bean
    fun defaultAwsCredentialsProvider(@Value("\${babbage.aws.auth.profile:default}") profile: String)
            : AwsCredentialsProvider {
        return AwsCredentialsProviderChain
            .builder()
            .credentialsProviders(
                // instance profile is also needed for people not using ecs but directly using ec2 instances!!
                ContainerCredentialsProvider.builder().build(),
                InstanceProfileCredentialsProvider.builder().build(),
                EnvironmentVariableCredentialsProvider.create(),
                ProfileCredentialsProvider
                    .builder()
                    .profileName(profile)
                    .build()
            )
            .build()
    }
}
