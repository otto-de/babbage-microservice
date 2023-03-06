package de.otto.babbage.core.status.contributors

import de.otto.babbage.core.status.indicators.Status
import de.otto.babbage.core.status.indicators.StatusDetail
import de.otto.babbage.core.status.indicators.StatusDetailIndicator
import org.springframework.boot.actuate.info.Info
import org.springframework.boot.actuate.info.InfoContributor
import org.springframework.stereotype.Component

@Component
class StatusDetailContributor(
    private val statusDetailIndicators: List<StatusDetailIndicator>
) : InfoContributor {

    companion object {
        private val STATUS_PRIO_ORDER = listOf(Status.ERROR, Status.WARNING, Status.OK)
    }

    override fun contribute(builder: Info.Builder) {
        val statusDetails: Map<String, List<StatusDetail>> = statusDetailIndicators.groupBy { it.getGroup() }
            .map { it.key to it.value.flatMap { indicator -> indicator.getDetails() } }
            .toMap()
        builder.withDetail("statusDetails", statusDetails)
        builder.withDetail("status", findWorstStatus(statusDetails))
    }

    private fun findWorstStatus(statusDetails: Map<String, List<StatusDetail>>): Status {
        val foundStatusList = statusDetails.flatMap { it.value }
            .map { it.status }
            .toSet()
        STATUS_PRIO_ORDER.forEach {
            if (foundStatusList.contains(it)) {
                return it
            }
        }
        return Status.OK
    }
}
