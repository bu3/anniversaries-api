package io.pivotal.employees

import spock.lang.Specification

import java.time.LocalDate


class EmployeeControllerTest extends Specification {

    def "Should add a new employee"() {
        given:
        def employee = new Employee(null, 'Foo', LocalDate.now())
        def expectedEmployee = new Employee(1, 'Foo', LocalDate.now())
        def employeeService = Mock(EmployeeService)
        def employeeController = new EmployeeController(employeeService)

        when:
        def result = employeeController.addEmployee(employee)

        then:
        1 * employeeService.store(employee) >> expectedEmployee
        result == expectedEmployee
    }
}
