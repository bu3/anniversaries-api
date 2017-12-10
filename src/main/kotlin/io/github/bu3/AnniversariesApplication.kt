package io.github.bu3

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters

@EntityScan(
    basePackageClasses = arrayOf(AnniversariesApplication::class, Jsr310JpaConverters::class)
)
@SpringBootApplication
class AnniversariesApplication

fun main(args: Array<String>) {
    SpringApplication.run(AnniversariesApplication::class.java, *args)
}
