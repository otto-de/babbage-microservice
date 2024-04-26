package de.otto.babbage.core.status

import de.otto.babbage.core.status.contributors.SystemInfo
import de.otto.babbage.core.status.indicators.StatusDetail
import de.otto.babbage.core.status.version.GitVersion

data class StatusData(
    val application: Application,
    val indicators: Map<String, List<StatusDetail>>,
    val system: SystemInfo,
    val serviceStatus: de.otto.babbage.core.status.indicators.Status
)

data class Application(
    val name: String,
    val version: GitVersion? = null
)
