package io.github.bu3.employees

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters

@EntityScan(
    basePackageClasses = arrayOf(EmployeesApplication::class, Jsr310JpaConverters::class)
)
@SpringBootApplication
class EmployeesApplication

fun main(args: Array<String>) {
    SpringApplication.run(EmployeesApplication::class.java, *args)
}
