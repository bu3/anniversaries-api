package io.github.bu3.employees

import io.github.bu3.events.Aggregate
import spock.lang.Specification

import java.time.LocalDate

class DefaultEmployeeServiceTest extends Specification {

    EmployeeRepository employeeRepository
    EmployeeService employeeService

    void setup() {
        employeeRepository = Mock(EmployeeRepository)
        employeeService = new DefaultEmployeeService(employeeRepository)
    }

    def "Should save a new employee"() {
        given:
        def aggregate = new Aggregate("1", 'Foo', "photo Url", LocalDate.now())
        when:
        employeeService.handleEmployeeCreatedEvent(aggregate)

        then:
        1 * employeeRepository.save(_ as Employee) >> { Employee employee ->

            assert employee.id == aggregate.id
            assert employee.name == aggregate.name
            assert employee.hireDate == aggregate.hireDate
            assert employee.photoURL == aggregate.photoURL

            employee
        }
    }

    def "Should load employees"() {
        given:
        def employee = new Employee("1", 'foo', "photo Url", LocalDate.of(2016, 1, 1))

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
        def aggregate = new Aggregate("1", 'Foo', "photo Url", LocalDate.now())

        when:
        employeeService.handleEmployeeDeletedEvent(aggregate)

        then:
        1 * employeeRepository.delete("1")
    }

    def "Should delete all"() {
        when:
        employeeService.handleAllEmployeesDeletedEvent()

        then:
        1 * employeeRepository.deleteAll()
    }

    def "Should find employee by id"() {
        given:
        def employee = new Employee("1", 'foo', "photo Url", LocalDate.of(2016, 1, 1))

        when:
        def result = employeeService.findById("1")

        then:
        1 * employeeRepository.findOne("1") >> employee
        result == employee
    }
}
