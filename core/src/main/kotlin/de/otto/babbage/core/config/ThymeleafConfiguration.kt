package de.otto.babbage.core.config

import org.springframework.context.annotation.Configuration
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
import org.thymeleaf.spring6.SpringTemplateEngine


@Configuration
class ThymeleafConfiguration(springTemplateEngine: SpringTemplateEngine) {

    init {
        springTemplateEngine.addDialect(Java8TimeDialect())
    }

}