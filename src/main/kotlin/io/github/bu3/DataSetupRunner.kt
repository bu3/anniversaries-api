package io.github.bu3

import io.github.bu3.employees.Employee
import io.github.bu3.employees.EmployeeService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Profile("default", "cloud")
@Component
class DataSetupRunner(val employeeService: EmployeeService) : ApplicationRunner {

    val employees = listOf(
            Employee(name = "John Doe", hireDate = LocalDate.of(2015, 11, 1), photoURL = ""),
            Employee(name = "Tizio", hireDate = LocalDate.of(2014, 1, 1), photoURL = "https://vignette.wikia.nocookie.net/mario/images/3/3e/487px-Unknown_Shy_Guy.PNG/revision/latest?cb=20140518142233"),
            Employee(name = "Caio", hireDate = LocalDate.of(2016, 3, 1), photoURL = "https://orig00.deviantart.net/efab/f/2010/076/3/6/mister_w_by_letourbillonenchant.jpg"),
            Employee(name = "Sempronio", hireDate = LocalDate.of(2015, 12, 1), photoURL = "https://vignette1.wikia.nocookie.net/supermarioitalia/images/9/9d/Sempronio.PNG/revision/latest?cb=20120329122905&path-prefix=it")
    )

    override fun run(args: ApplicationArguments?) {
        employees.forEach { this.employeeService.store(it) }
    }
}


