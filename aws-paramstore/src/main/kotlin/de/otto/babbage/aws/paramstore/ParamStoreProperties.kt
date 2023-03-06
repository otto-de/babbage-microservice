package de.otto.babbage.aws.paramstore

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "babbage.aws.paramstore")
class ParamStoreProperties {
    var path: String = ""
    var isAddWithLowestPrecedence: Boolean = false
}
