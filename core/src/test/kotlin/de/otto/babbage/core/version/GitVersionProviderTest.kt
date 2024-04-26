package de.otto.babbage.core.version

import de.otto.babbage.core.status.version.GitVersion
import de.otto.babbage.core.status.version.GitVersionProvider
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.boot.info.GitProperties
import java.util.*

class GitVersionProviderTest {

    @Test
    fun `should convert git properties to git version`() {
        // given
        val properties = Properties()
        val branchName = "someBranch-${UUID.randomUUID()}"
        properties.setProperty("branch", branchName)
        val commitId = "someCommitId-${UUID.randomUUID()}"
        properties.setProperty("commit.id", commitId)
        val commitTime = "someCommitTime-${UUID.randomUUID()}"
        properties.setProperty("commit.time", commitTime)
        val user = "someUser-${UUID.randomUUID()}"
        properties.setProperty("commit.user.name", user)
        val messageShort = "someMessageShort-${UUID.randomUUID()}"
        properties.setProperty("commit.message.short", messageShort)
        val remoteUrl = "https://github.com/otto-de/babbage-microservice.git"
        properties.setProperty("remote.origin.url", remoteUrl)
        val gitProperties = GitProperties(properties)

        val versionProvider = GitVersionProvider(gitProperties)

        // when
        val gitVersion = versionProvider.getGitVersion()

        // then
        gitVersion shouldBe GitVersion(
            branch = branchName,
            commitId = commitId,
            time = commitTime,
            user = user,
            message = messageShort,
            repositoryUrl = remoteUrl
        )
    }

    @ParameterizedTest
    @CsvSource(
        "git@github.com:otto-de/babbage-microservice.git, https://github.com/otto-de/babbage-microservice.git",
        "git@gitlab.com:otto-de/babbage-microservice.git, https://gitlab.com/otto-de/babbage-microservice.git",
        "https://github.com/otto-de/babbage-microservice.git, https://github.com/otto-de/babbage-microservice.git"
    )
    fun `should parse remote url to https url to link directly to web page of repository`(
        gitRemoteUrl: String,
        expectedRepositoryUrl: String
    ) {
        // given
        val properties = Properties()
        properties.setProperty("branch", "someBranch-${UUID.randomUUID()}")
        properties.setProperty("commit.id", "someCommitId-${UUID.randomUUID()}")
        properties.setProperty("commit.time", "someCommitTime-${UUID.randomUUID()}")
        properties.setProperty("commit.user.name", "someUser-${UUID.randomUUID()}")
        properties.setProperty("commit.message.short", "someMessageShort-${UUID.randomUUID()}")
        properties.setProperty("remote.origin.url", gitRemoteUrl)
        val gitProperties = GitProperties(properties)

        val versionProvider = GitVersionProvider(gitProperties)

        // when
        val gitVersion = versionProvider.getGitVersion()

        // then
        gitVersion!!.repositoryUrl shouldBe expectedRepositoryUrl
    }

    @Test
    fun `should catch missing git properties and return null`() {
        // given
        val gitProperties = GitProperties(Properties())
        val versionProvider = GitVersionProvider(gitProperties)

        // when
        val gitVersion = versionProvider.getGitVersion()

        // then
        gitVersion shouldBe null
    }
}
