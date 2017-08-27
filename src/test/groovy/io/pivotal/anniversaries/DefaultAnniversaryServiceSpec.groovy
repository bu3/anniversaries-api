package io.pivotal.anniversaries

import io.pivotal.employees.Employee
import io.pivotal.employees.EmployeeService
import spock.lang.Specification

import java.time.LocalDate

class DefaultAnniversaryServiceSpec extends Specification {

    def "Should load data from database"() {
        given:
        def anniversaryDTO = new Employee(1, 'foo', LocalDate.of(2016,1,1))
        EmployeeService employeeService = Mock(EmployeeService)
        AnniversaryService anniversaryService = new DefaultAnniversaryService(employeeService)

        when:
        def anniversaries = anniversaryService.loadAnniversaries()

        then:
        1 * employeeService.loadEmployees() >> [anniversaryDTO]
        anniversaries[0].id == anniversaryDTO.id
        anniversaries[0].name == anniversaryDTO.name
        anniversaries[0].hireDate == anniversaryDTO.hireDate
        anniversaries[0].anniversaryDate == LocalDate.of(2018, 1, 1)
    }
}
