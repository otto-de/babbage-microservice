package de.otto.babbage.core.management

import org.springframework.web.bind.annotation.RequestMapping

/**
 * Use this interface for all controllers that should be part of the management area.
 *
 * If this interface is added to a controller, global model attributes for the navigation of a management page are
 * added via GlobalModelAttributes.
 *
 * @see de.otto.babbage.core.management.GlobalModelAttributes
 */
interface ManagementController
