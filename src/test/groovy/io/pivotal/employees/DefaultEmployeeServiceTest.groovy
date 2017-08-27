package io.pivotal.employees

import spock.lang.Specification

import java.time.LocalDate


class DefaultEmployeeServiceTest extends Specification {

    def "Should save a new employee"() {

        given:
        def employee = new Employee(null, 'Foo', LocalDate.now())
        def expectedEmployee = new Employee(1, 'Foo', LocalDate.now())
        def employeeRepository = Mock(EmployeeRepository)
        def employeeService = new DefaultEmployeeService(employeeRepository)

        when:
        def result = employeeService.store(employee)

        then:
        1 * employeeRepository.save(employee) >> expectedEmployee
        result == expectedEmployee

    }
}
