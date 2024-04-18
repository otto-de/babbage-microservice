package de.otto.babbage.example

import de.otto.babbage.core.status.indicators.Status
import de.otto.babbage.core.status.indicators.StatusDetail
import de.otto.babbage.core.status.indicators.StatusDetailIndicator
import org.springframework.stereotype.Component

@Component
class BathroomStatusDetailIndicator : StatusDetailIndicator {
    override fun getGroup() = "bathroom"

    override fun getDetails(): List<StatusDetail> {
        return listOf(
            StatusDetail(
                name = "shower",
                status = Status.ERROR,
                message = "The water is cold.",
                additionalInfos = mapOf(
                    "water-temperature" to "4°C"
                )
            )
        )
    }
}