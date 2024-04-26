package de.otto.babbage.core.status.version

data class GitVersion(
    val branch: String,
    val commitId: String,
    val time: String,
    val user: String,
    val message: String,
    val repositoryUrl: String
)
