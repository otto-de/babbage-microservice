package de.otto.babbage.core.status.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "babbage.status")
class StatusProperties {

    var useCommitAsVersion = false
}
