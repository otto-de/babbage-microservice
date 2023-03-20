package de.otto.babbage.example

import de.otto.babbage.core.management.ManagementController
import de.otto.babbage.core.management.NavbarItem
import de.otto.babbage.core.management.NavbarItemType
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CustomManagementController : ManagementController {

    @Bean
    fun customMainNavItem() = NavbarItem(
        path = "/custom",
        name = "Custom Page",
        type = NavbarItemType.MAIN
    )

    @Bean
    fun customSubNavItem() = NavbarItem(
        path = "/custom2",
        name = "Custom Page 2",
        type = NavbarItemType.SUB
    )

    @GetMapping("\${management.endpoints.web.base-path}/custom")
    fun customManagementPage(model: Model): String {
        model.addAttribute("customAttribute", "Hello World!")
        return "custom"
    }

    @GetMapping("\${management.endpoints.web.base-path}/custom2")
    fun custom2ManagementPage(model: Model): String {
        model.addAttribute("customAttribute", "Again, hello World!")
        return "custom"
    }

}
