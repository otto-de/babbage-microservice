package de.otto.babbage.core.config

import de.otto.babbage.core.loggers.LoggerController
import de.otto.babbage.core.management.NavbarItem
import de.otto.babbage.core.management.NavbarItemType
import de.otto.babbage.core.status.StatusController
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NavbarItemConfiguration {

    companion object {
        const val STATUS_NAVBAR_ITEM_ORDER = 1
        const val LOGGER_NAVBAR_ITEM_ORDER = 1
    }

    @Bean
    @ConditionalOnBean(StatusController::class)
    fun statusNavbarItem() = NavbarItem(
        path = "/status",
        name = "Status",
        type = NavbarItemType.MAIN,
        order = STATUS_NAVBAR_ITEM_ORDER
    )

    @Bean
    @ConditionalOnBean(LoggerController::class)
    fun loggerNavbarItem() = NavbarItem(
        path = "/logger",
        name = "Logger",
        type = NavbarItemType.SUB,
        order = LOGGER_NAVBAR_ITEM_ORDER
    )

}
