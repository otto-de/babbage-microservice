package de.otto.babbage.core.status

import de.otto.babbage.core.status.contributors.SystemInfo
import de.otto.babbage.core.status.indicators.StatusDetail
import org.springframework.boot.actuate.health.Status

data class StatusData(
    val application: Application,
    val indicators: Map<String, List<StatusDetail>>,
    val system: SystemInfo,
    val serviceStatus: de.otto.babbage.core.status.indicators.Status
)

data class Application(
    val name: String,
    val version: String,
    val status: ApplicationStatus,
    val commit: String
)

enum class ApplicationStatus {
    OK, WARNING, ERROR;

    companion object {
        fun fromHealth(status: Status): ApplicationStatus {
            return when (status) {
                Status.DOWN, Status.OUT_OF_SERVICE, Status.UNKNOWN -> ERROR
                Status.UP -> OK
                else -> ERROR
            }
        }
    }
}
