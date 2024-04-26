package de.otto.babbage.core.status.version

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.info.GitProperties
import org.springframework.stereotype.Component

private val LOG: Logger = LoggerFactory.getLogger(GitVersionProvider::class.java)

@Component
@ConditionalOnProperty(prefix = "babbage.status.git", name = ["enabled"], havingValue = "true", matchIfMissing = false)
class GitVersionProvider(
    private val gitProperties: GitProperties
) {

    @Suppress("ReturnCount")
    fun getGitVersion(): GitVersion? {
        return GitVersion(
            branch = gitProperties.branch ?: return handleNull("branch"),
            commitId = gitProperties.get("commit.id") ?: return handleNull("commit.id"),
            time = gitProperties.get("commit.time") ?: return handleNull("commit.time"),
            user = gitProperties.get("commit.user.name") ?: return handleNull("commit.user.name"),
            message = gitProperties.get("commit.message.short") ?: return handleNull("commit.message.short"),
            repositoryUrl = parseRemoteUrlToHttps() ?: return handleNull("remote.origin.url")
        )
    }

    private fun parseRemoteUrlToHttps(): String? {
        val remoteUrl = gitProperties.get("remote.origin.url")
        if (remoteUrl?.startsWith("git@") == true) {
            val urlParts = remoteUrl.split(":")
            val hostPath = urlParts[0].replace("git@", "https://")
            val repositoryPath = urlParts[1]
            return "$hostPath/$repositoryPath"
        }
        return remoteUrl
    }

    private fun handleNull(fieldName: String): GitVersion? {
        LOG.warn("Missing $fieldName in git.properties. Git Information are not show on the status page.")
        return null
    }

}

