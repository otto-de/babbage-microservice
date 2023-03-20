package de.otto.babbage.core.management

data class NavbarItem(
    /**
     * The path is relative to the management.endpoints.web.base-path.
     */
    val path: String,
    /**
     * Display name for the navbar item.
     */
    val name: String,
    val type: NavbarItemType,
    /**
     * Order of the items in the main or sub navigation.
     */
    val order: Int = Int.MAX_VALUE
)
