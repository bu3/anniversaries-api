package io.pivotal.anniversaries

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AnniversariesApplication

fun main(args: Array<String>) {
    SpringApplication.run(AnniversariesApplication::class.java, *args)
}
