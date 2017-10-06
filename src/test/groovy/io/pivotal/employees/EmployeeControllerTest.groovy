package io.pivotal.employees

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
        def employee = new Employee(null, 'Foo', LocalDate.now())
        def expectedEmployee = new Employee(1, 'Foo', LocalDate.now())

        when:
        def result = employeeController.addEmployee(employee)

        then:
        1 * employeeService.store(employee) >> expectedEmployee
        result == expectedEmployee
    }

    def "Should return a list of employees"() {
        given:
        def employees = [new Employee(1, 'Foo', LocalDate.MIN)]

        when:
        def response = employeeController.loadEmployees()

        then:
        1 * employeeService.loadEmployees() >> employees
        response == employees
    }

    def "Should delete an employee"() {
        given:
        def employee = new Employee(1, 'Foo', LocalDate.MIN)

        when:
        employeeController.deleteEmployee(employee)

        then:
        1 * employeeService.delete(employee)
    }
}
