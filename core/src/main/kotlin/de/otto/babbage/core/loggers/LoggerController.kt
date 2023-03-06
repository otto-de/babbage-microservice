package de.otto.babbage.core.loggers

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.logging.LoggersEndpoint
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(path = ["\${management.endpoints.web.base-path}/logger"])
@ConditionalOnProperty("management.endpoints.loggers.enabled", havingValue = "true", matchIfMissing = false)
class LoggerController(
    val loggersEndpoint: LoggersEndpoint,
    @Value("\${spring.webflux.base-path:}") val basePath: String,
    @Value("\${management.endpoints.web.base-path}") val managementBasePath: String
) {

    @GetMapping
    suspend fun listLoggers(model: Model): String {
        model.addAttribute("loggers", loggersEndpoint.loggers())
        model.addAttribute("basePath", basePath)
        model.addAttribute("managementBasePath", managementBasePath)
        return "logger-console"
    }
}
