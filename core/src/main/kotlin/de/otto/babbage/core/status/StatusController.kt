package de.otto.babbage.core.status

import de.otto.babbage.core.management.ManagementController
import de.otto.babbage.core.status.contributors.SystemInfoContributor
import de.otto.babbage.core.status.indicators.Status
import de.otto.babbage.core.status.indicators.Status.*
import de.otto.babbage.core.status.indicators.StatusDetail
import de.otto.babbage.core.status.indicators.StatusDetailIndicator
import de.otto.babbage.core.status.version.GitVersionProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class StatusController(
    @Autowired(required = false)
    private val gitVersionProvider: GitVersionProvider? = null,
    @Value("\${info.app.name}") private val applicationName: String,
    private val statusDetailIndicators: List<StatusDetailIndicator>,
    private val systemInfoContributor: SystemInfoContributor
) : ManagementController {

    @GetMapping("\${management.endpoints.web.base-path}/status")
    @Suppress("FunctionOnlyReturningConstant", "UnusedPrivateMember")
    suspend fun status(model: Model): String {
        val statusData = statusJson()
        model.addAttribute("statusData", statusData)
        return "status"
    }

    @GetMapping("\${management.endpoints.web.base-path}/status", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    suspend fun statusJson(): StatusData {
        val indicators = statusDetailIndicators.groupBy { it.getGroup() }
            .map { it.key to it.value.flatMap { indicator -> withContext(Dispatchers.IO) { indicator.getDetails() } } }
            .toMap()
        return StatusData(
            application = Application(
                name = applicationName,
                version = gitVersionProvider?.getGitVersion()
            ),
            serviceStatus = getWorstStatus(indicators),
            indicators = indicators,
            system = systemInfoContributor.getSystemInfo()
        )
    }

    private fun getWorstStatus(statusDetails: Map<String, List<StatusDetail>>): Status {
        return statusDetails.values.flatMap { statusDetail -> statusDetail.map { it.status } }
            .toSet()
            .worstStatus()
    }

}

private fun Set<Status>.worstStatus(): Status {
    return if (this.contains(ERROR)) {
        ERROR
    } else if (this.contains(WARNING)) {
        WARNING
    } else {
        OK
    }
}
