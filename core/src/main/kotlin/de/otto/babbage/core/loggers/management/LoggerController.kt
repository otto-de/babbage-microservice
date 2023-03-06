package de.otto.babbage.core.loggers.management

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.logging.LoggersEndpoint
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(path = ["\${management.endpoints.web.base-path}/logger"])
class LoggerController(
    val loggersEndpoint: LoggersEndpoint,
    @Value("\${spring.webflux.base-path}") val basePath: String
) {

    @GetMapping
    suspend fun listLoggers(model: Model): String {
        model.addAttribute("loggers", loggersEndpoint.loggers())
        model.addAttribute("basePath", basePath)
        return "logger-console"
    }
}
