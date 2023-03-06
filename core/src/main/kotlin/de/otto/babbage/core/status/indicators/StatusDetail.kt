package de.otto.babbage.core.status.indicators

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class StatusDetail(
    val name: String,
    val status: Status,
    val message: String,
    val additionalInfos: Map<String, String>? = null
)
