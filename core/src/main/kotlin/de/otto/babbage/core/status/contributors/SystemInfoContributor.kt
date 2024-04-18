package de.otto.babbage.core.status.contributors

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.info.Info
import org.springframework.boot.actuate.info.InfoContributor
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.OffsetDateTime
import java.time.OffsetDateTime.now
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val ONE_MINUTE_SECONDS = 60
private const val ONE_HOUR_SECONDS = ONE_MINUTE_SECONDS * 60

@Component
class SystemInfoContributor(
    @Value("\${server.port:0}")
    private val port: Int,
    @Value("\${server.hostname:}")
    private val hostname: String
) : InfoContributor {

    companion object {
        private val START_TIME = now()
    }

    override fun contribute(builder: Info.Builder) {
        builder.withDetail(
            "system",
            getSystemInfo().asMap()
        )
    }

    fun getSystemInfo(): SystemInfo {
        return SystemInfo(
            startTime = START_TIME,
            upTime = getSystemUpTime(),
            systemTime = now(),
            port = port,
            hostname = hostname
        )
    }

    private fun getSystemUpTime(): String {
        val seconds = Duration.between(START_TIME, now()).seconds
        return String.format(
            Locale.getDefault(),
            "%d:%02d:%02d",
            seconds / ONE_HOUR_SECONDS,
            seconds % ONE_HOUR_SECONDS / ONE_MINUTE_SECONDS,
            seconds % ONE_MINUTE_SECONDS
        )
    }
}

data class SystemInfo(
    val startTime: OffsetDateTime,
    val upTime: String,
    val systemTime: OffsetDateTime,
    val port: Int,
    val hostname: String
) {

    fun asMap(): Map<String, String> {
        return mapOf(
            "startTime" to startTime.format(DateTimeFormatter.ISO_DATE_TIME),
            "upTime" to upTime,
            "systemTime" to systemTime.format(DateTimeFormatter.ISO_DATE_TIME),
            "port" to port.toString(),
            "hostname" to hostname
        )
    }

}
