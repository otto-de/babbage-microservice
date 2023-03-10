package de.otto.babbage.core.status

import de.otto.babbage.core.management.ManagementController
import de.otto.babbage.core.status.config.StatusProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.info.InfoEndpoint
import org.springframework.boot.info.GitProperties
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class StatusController(
    private val infoEndpoint: InfoEndpoint,
    @Autowired(required = false)
    private val gitProperties: GitProperties?,
    private val statusProperties: StatusProperties,
    @Value("\${info.app.name}") private val applicationName: String
) : ManagementController {

    @GetMapping("\${management.endpoints.web.base-path}/status")
    @Suppress("FunctionOnlyReturningConstant", "UnusedPrivateMember")
    suspend fun status(model: Model): String {
        val statusData = statusJson()
        model.addAttribute("applicationStatus", statusData.application.status)
        model.addAttribute("applicationCommit", statusData.application.commit)
        model.addAttribute("applicationVersion", statusData.application.version)

        return "status"
    }

    @GetMapping("\${management.endpoints.web.base-path}/status", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    suspend fun statusJson(): StatusData {
        val commit = gitProperties?.commitId ?: "unavailable"
        val version = if (statusProperties.useCommitAsVersion) commit else getVersion()
        return StatusData(
            application = Application(
                name = applicationName,
                version = version,
                status = ApplicationStatus.OK, // dummy status because HealthEndpoint is blocking and causing errors
                commit = commit
            )
        )
    }

    @Suppress("UNCHECKED_CAST", "ReturnCount")
    private fun getVersion(): String {
        val gitInfos = infoEndpoint.info()["git"]?.let { it as Map<String, Any> } ?: return ""
        val commitInfos = gitInfos["commit"]?.let { it as Map<String, Any> } ?: return ""
        return commitInfos["time"]?.toString() ?: ""
    }
}
