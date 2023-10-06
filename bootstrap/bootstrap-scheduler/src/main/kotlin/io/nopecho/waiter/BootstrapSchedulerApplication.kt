package io.nopecho.waiter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class BootstrapSchedulerApplication

fun main(args: Array<String>) {
    runApplication<BootstrapSchedulerApplication>(*args)
}