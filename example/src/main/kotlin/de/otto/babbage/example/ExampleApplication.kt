package de.otto.babbage.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("de.otto.babbage")
class ExampleApplication

fun main(args: Array<String>) {
	@Suppress("SpreadOperator")
	runApplication<ExampleApplication>(*args)
}
