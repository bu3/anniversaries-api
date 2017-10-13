package io.github.bu3.employees

import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

import java.time.LocalDate

class DefaultEmployeeServiceTest extends Specification {

    def employeeRepository
    def publisher
    def employeeService

    void setup() {
        employeeRepository = Mock(EmployeeRepository)
        publisher = Mock(ApplicationEventPublisher)
        employeeService = new DefaultEmployeeService(employeeRepository, publisher)
    }

    def "Should save a new employee"() {
        given:
        def employee = new Employee(null, 'Foo', LocalDate.now())
        def expectedEmployee = new Employee(1, 'Foo', LocalDate.now())

        when:
        def result = employeeService.store(employee)

        then:
        1 * employeeRepository.save(employee) >> expectedEmployee
        1 * publisher.publishEvent(_ as EmployeeCreatedEvent) >> { args ->
            assert args[0].employee == expectedEmployee
        }
        result == expectedEmployee
    }

    def "Should load employees"() {
        given:
        def employee = new Employee(1, 'foo', LocalDate.of(2016, 1, 1))

        when:
        def employees = employeeService.loadEmployees()

        then:
        1 * employeeRepository.findAll() >> [employee]
        employees[0].id == employee.id
        employees[0].name == employee.name
        employees[0].hireDate == employee.hireDate
    }

    def "Should delete an employee"() {
        given:
        def employee = new Employee(1, 'Foo', LocalDate.MIN)

        when:
        employeeService.delete(employee)

        then:
        1 * employeeRepository.delete(employee)
        1 * publisher.publishEvent(_ as EmployeeDeletedEvent) >> { args ->
            assert args[0].employee == employee
        }
    }
}
