package de.otto.babbage.validation.configuration

import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ValidationExceptionControllerAdvice {

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleExceptionForInvalidParameter(): ResponseEntity<*> {
        return ResponseEntity.badRequest().build<Any>()
    }

}
