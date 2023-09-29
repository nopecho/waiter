package io.nopecho.waiter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootstrapApiApplication

fun main(args: Array<String>) {
    runApplication<BootstrapApiApplication>(*args)
}