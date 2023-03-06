package de.otto.babbage.aws.paramstore

import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Environment
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.util.ObjectUtils
import software.amazon.awssdk.services.ssm.SsmClient
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest
import software.amazon.awssdk.services.ssm.model.Parameter
import software.amazon.awssdk.services.ssm.model.ParameterType
import java.util.*

class ParamStorePropertySourcePostProcessor(
    private val ssmClient: SsmClient
) : BeanFactoryPostProcessor, EnvironmentAware {

    private lateinit var properties: ParamStoreProperties

    @Throws(BeansException::class)
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val propertiesSource = Properties()
        val requestBuilder = GetParametersByPathRequest
            .builder()
            .path(properties.path)
            .recursive(true)
            .withDecryption(true)
        val firstPage = ssmClient.getParametersByPath(requestBuilder.build())
        addParametersToPropertiesSource(propertiesSource, firstPage.parameters())

        var nextToken = firstPage.nextToken()

        while (!ObjectUtils.isEmpty(nextToken)) {
            val nextPage = ssmClient.getParametersByPath(
                requestBuilder
                    .nextToken(nextToken)
                    .build()
            )
            addParametersToPropertiesSource(propertiesSource, nextPage.parameters())
            nextToken = nextPage.nextToken()
        }

        val env = beanFactory.getBean(ConfigurableEnvironment::class.java)
        val propertySources = env.propertySources

        if (properties.isAddWithLowestPrecedence) {
            propertySources.addLast(PropertiesPropertySource(PARAMETER_STORE_PROPERTY_SOURCE, propertiesSource))
        } else {
            propertySources.addFirst(PropertiesPropertySource(PARAMETER_STORE_PROPERTY_SOURCE, propertiesSource))
        }
    }

    private fun addParametersToPropertiesSource(propertiesSource: Properties, parameters: List<Parameter>) {
        parameters.forEach { p ->
            val name = p.name().substring(properties.path.length + 1)
            val loggingValue = if (ParameterType.SECURE_STRING == p.type()) "*****" else p.value()
            LOG.info("Loaded {} from ParametersStore, value='{}', length={}", name, loggingValue, p.value().length)
            propertiesSource.setProperty(name, p.value())
        }
    }

    override fun setEnvironment(environment: Environment) {
        val pathProperty = "babbage.aws.paramstore.path"
        val path = Objects.requireNonNull(
            environment.getProperty(pathProperty),
            "Property '$pathProperty' must not be null"
        )
        properties = ParamStoreProperties()
        properties.isAddWithLowestPrecedence =
            environment.getProperty("babbage.aws.paramstore.addWithLowestPrecedence", "false").toBooleanStrict()
        properties.path = path!!
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(ParamStorePropertySourcePostProcessor::class.java)
        private const val PARAMETER_STORE_PROPERTY_SOURCE = "paramStorePropertySource"
    }
}
