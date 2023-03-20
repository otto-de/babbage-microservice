package de.otto.babbage.core.management

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice(assignableTypes = [ManagementController::class])
class GlobalModelAttributes(
    private val webEndpointProperties: WebEndpointProperties,
    @Value("\${info.app.name}") private val applicationName: String,
    @Value("\${spring.webflux.base-path:}") val basePath: String,
    navbarItems: List<NavbarItem>
) {

    private val mainNavbarItems: List<NavbarItem> = navbarItems
        .filter { it.type == NavbarItemType.MAIN }
        .sortedBy { it.order }
    private val subNavbarItems: List<NavbarItem> = navbarItems
        .filter { it.type == NavbarItemType.SUB }
        .sortedBy { it.order }

    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("managementBasePath", webEndpointProperties.basePath)
        model.addAttribute("basePath", basePath)
        model.addAttribute("applicationName", applicationName)
        model.addAttribute("mainNavbarItems", mainNavbarItems)
        model.addAttribute("subNavbarItems", subNavbarItems)
    }

}
