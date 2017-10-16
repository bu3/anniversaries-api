package io.github.bu3.employees

import spock.lang.Specification

import java.time.LocalDate


class EmployeeControllerTest extends Specification {

    def employeeService
    def employeeController

    void setup() {
        employeeService = Mock(EmployeeService)
        employeeController = new EmployeeController(employeeService)
    }

    def "Should add a new employee"() {
        given:
        def employee = new Employee(null, 'Foo', "photo url", LocalDate.now())
        def expectedEmployee = new Employee(1, 'Foo', "photo url", LocalDate.now())

        when:
        def result = employeeController.addEmployee(employee)

        then:
        1 * employeeService.store(employee) >> expectedEmployee
        result == expectedEmployee
    }

    def "Should return a list of employees"() {
        given:
        def employees = [new Employee(1, 'Foo', "photo url", LocalDate.MIN)]

        when:
        def response = employeeController.loadEmployees()

        then:
        1 * employeeService.loadEmployees() >> employees
        response == employees
    }

    def "Should delete an employee"() {
        given:
        def employee = new Employee(1, 'Foo', "photo url", LocalDate.MIN)

        when:
        employeeController.deleteEmployee(employee)

        then:
        1 * employeeService.delete(employee)
    }
}
