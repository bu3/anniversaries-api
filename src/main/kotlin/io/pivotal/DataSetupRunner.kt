package io.pivotal

import io.pivotal.employees.Employee
import io.pivotal.employees.EmployeeService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Profile("default", "cloud")
@Component
class DataSetupRunner(val employeeService: EmployeeService) : ApplicationRunner {

    val employees = listOf(
            Employee(name = "John Doe", hireDate = LocalDate.of(2015,11,1)),
            Employee(name = "Tizio", hireDate = LocalDate.of(2014,1,1)),
            Employee(name = "Caio", hireDate = LocalDate.of(2016,3,1)),
            Employee(name = "Sempronio", hireDate = LocalDate.of(2015,12,1))
    )

    override fun run(args: ApplicationArguments?) {
        employees.forEach { this.employeeService.store(it) }
    }
}


