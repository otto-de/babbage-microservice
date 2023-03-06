package de.otto.babbage.core.status

import org.springframework.boot.actuate.health.Status

data class StatusData(
    val application: Application
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
