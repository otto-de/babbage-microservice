package de.otto.babbage.core.status.indicators

interface StatusDetailIndicator {

    fun getGroup(): String
    fun getDetails(): List<StatusDetail>
}
