package de.otto.babbage.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    (runApplication<TestApplication>(*args))
}
