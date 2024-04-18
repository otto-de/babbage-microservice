package de.otto.babbage.core.status.indicators

enum class Status {
    OK, WARNING, ERROR;

    fun isOk() = this == OK

    fun isWarning() = this == WARNING

    fun isError() = this == ERROR

}
