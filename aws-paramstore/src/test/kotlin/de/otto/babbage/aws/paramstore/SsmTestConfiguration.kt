package de.otto.babbage.aws.paramstore

import io.mockk.every
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import software.amazon.awssdk.services.ssm.SsmClient
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest
import software.amazon.awssdk.services.ssm.model.GetParametersByPathResponse
import software.amazon.awssdk.services.ssm.model.Parameter

@Configuration
class SsmTestConfiguration {

    @Bean
    fun testSsmClient(): SsmClient {
        val mock = mockk<SsmClient>(relaxUnitFun = true)
        every { mock.getParametersByPath(any<GetParametersByPathRequest>()) } answers {
            GetParametersByPathResponse.builder()
                .parameters(Parameter.builder().name("mongo/password").value("secret").build())
                .build()
        }
        return mock
    }

}
