package de.otto.babbage.core.status

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class GlobalModelAttributes(
    private val webEndpointProperties: WebEndpointProperties,
    @Value("\${info.app.name}") private val applicationName: String
) {

    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("webEndpointBasePath", webEndpointProperties.basePath)
        model.addAttribute("applicationName", applicationName)
    }

}
