package de.otto.babbage.core.loggers

import de.otto.babbage.core.management.ManagementController
import org.springframework.boot.actuate.logging.LoggersEndpoint
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(path = ["\${management.endpoints.web.base-path}/logger"])
@ConditionalOnProperty("management.endpoints.loggers.enabled", havingValue = "true", matchIfMissing = false)
class LoggerController(val loggersEndpoint: LoggersEndpoint) : ManagementController {

    @GetMapping
    suspend fun listLoggers(model: Model): String {
        model.addAttribute("loggers", loggersEndpoint.loggers())
        return "logger-console"
    }
}
