package de.otto.babbage.example

import de.otto.babbage.core.status.indicators.Status
import de.otto.babbage.core.status.indicators.StatusDetail
import de.otto.babbage.core.status.indicators.StatusDetailIndicator
import org.springframework.stereotype.Component

@Component
class KitchenStatusDetailIndicator : StatusDetailIndicator {
    override fun getGroup() = "kitchen"

    override fun getDetails(): List<StatusDetail> {
        return listOf(
            StatusDetail(
                name = "toaster",
                status = Status.OK,
                message = "The toast is ready.",
                additionalInfos = mapOf(
                    "required-watt" to "1000W"
                )
            ),
            StatusDetail(
                name = "fridge",
                status = Status.WARNING,
                message = "Fridge is not cold enough.",
                additionalInfos = mapOf(
                    "temperature" to "8°C"
                )
            )
        )
    }
}