package de.otto.babbage.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class SafeIdValidator : ConstraintValidator<SafeId?, String?> {
    override fun initialize(safeId: SafeId?) {
        // do nothing
    }

    override fun isValid(id: String?, context: ConstraintValidatorContext?): Boolean {
        return if (id == null) {
            true
        } else IdPattern.matcher(id).matches()
    }

    companion object {
        private val IdPattern: java.util.regex.Pattern = java.util.regex.Pattern.compile("[a-zA-Z0-9\\-_.]*")
    }
}
