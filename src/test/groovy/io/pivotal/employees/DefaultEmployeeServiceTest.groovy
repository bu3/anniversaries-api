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

    def "Should load employees"() {
        given:
        def employee = new Employee(1, 'foo', LocalDate.of(2016,1,1))
        EmployeeRepository repository = Mock(EmployeeRepository)
        EmployeeService employeeService = new DefaultEmployeeService(repository)

        when:
        def employees = employeeService.loadEmployees()

        then:
        1 * repository.findAll() >> [employee]
        employees[0].id == employee.id
        employees[0].name == employee.name
        employees[0].hireDate == employee.hireDate
    }
}
