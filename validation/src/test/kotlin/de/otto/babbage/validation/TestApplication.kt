package de.otto.babbage.validation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    (runApplication<TestApplication>(*args))
}
