package de.otto.babbage.aws.paramstore

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ParamStorePropertySourcePostProcessorTest {
    private val context = AnnotationConfigApplicationContext()

    @ImportAutoConfiguration(SsmTestConfiguration::class, ParamStoreConfiguration::class)
    private class ParamStoreTestConfiguration

    @AfterEach
    fun close() {
        context.close()
    }

    @Test
    fun shouldLoadPropertiesFromParamStore() {
        context.register(ParamStoreTestConfiguration::class.java)
        TestPropertyValues
            .of("babbage.aws.paramstore.enabled=true")
            .and("babbage.aws.paramstore.path=mongo")
            .applyTo(context)
        context.refresh()
        context.containsBean("paramStorePropertySourcePostProcessor") shouldBe true
        context.environment.propertySources.contains("paramStorePropertySource") shouldBe true
        "secret" shouldBe context.environment.getProperty("password")
    }

    @Test
    fun shouldNotLoadPropertiesFromParamStore() {
        TestPropertyValues
            .of("babbage.aws.paramstore.enabled=false")
            .applyTo(context)
        context.refresh()
        context.containsBean("paramStorePropertySourcePostProcessor") shouldBe false
    }
}
