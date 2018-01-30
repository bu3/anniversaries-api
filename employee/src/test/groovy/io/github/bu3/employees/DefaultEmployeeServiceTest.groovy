package io.github.bu3.employees

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import spock.lang.Specification

import java.time.LocalDate

class DefaultEmployeeServiceTest extends Specification {

    EmployeeRepository employeeRepository
    def addEmployeeChannel
    def deleteEmployeeChannel
    def deleteAllEmployeesChannel
    def employeeService

    void setup() {
        employeeRepository = Mock(EmployeeRepository)
        addEmployeeChannel = Mock(MessageChannel)
        deleteEmployeeChannel = Mock(MessageChannel)
        deleteAllEmployeesChannel = Mock(MessageChannel)
        addEmployeeChannel = Mock(MessageChannel)
        employeeService = new DefaultEmployeeService(employeeRepository, addEmployeeChannel, deleteEmployeeChannel, deleteAllEmployeesChannel)
    }

    def "Should save a new employee"() {
        given:
        def employee = new Employee(null, 'Foo', "photo Url", LocalDate.now())
        def expectedEmployee = new Employee(1, 'Foo', "photo Url", LocalDate.now())

        when:
        def result = employeeService.store(employee)

        then:
        1 * employeeRepository.save(employee) >> expectedEmployee
        1 * addEmployeeChannel.send(_ as Message) >> { Message message ->
            def payload = message.payload
            assert payload.aggregateId != null
            assert payload.id != null
            assert payload.name == employee.name
            assert payload.photoURL == employee.photoURL
            assert payload.hireDate == employee.hireDate

            true
        }
        0 * deleteEmployeeChannel._
        0 * deleteAllEmployeesChannel._
        result == expectedEmployee
    }

    def "Should load employees"() {
        given:
        def employee = new Employee(1, 'foo', "photo Url", LocalDate.of(2016, 1, 1))

        when:
        def employees = employeeService.loadEmployees()

        then:
        0 * deleteEmployeeChannel._
        0 * deleteAllEmployeesChannel._
        1 * employeeRepository.findAll() >> [employee]
        employees[0].id == employee.id
        employees[0].name == employee.name
        employees[0].hireDate == employee.hireDate
    }

    def "Should delete an employee and send an event"() {
        given:
        def employee = new Employee(1, 'Foo', "photo Url", LocalDate.MIN)

        when:
        employeeService.delete(1)

        then:
        0 * addEmployeeChannel._
        0 * deleteAllEmployeesChannel._
        1 * employeeRepository.findOne(1) >> employee
        1 * employeeRepository.delete(1)
        1 * deleteEmployeeChannel.send(_ as Message) >> { Message message ->
            def payload = message.payload
            assert payload.aggregateId != null
            assert payload.id == employee.id
            assert payload.name == employee.name
            assert payload.photoURL == employee.photoURL
            assert payload.hireDate == employee.hireDate

            true
        }
    }

    def "Should not send an event if the employee does not exist when deleting"() {
        when:
        employeeService.delete(1)

        then:
        1 * employeeRepository.findOne(1) >> null
        1 * employeeRepository.delete(1)
        0 * addEmployeeChannel._
        0 * deleteEmployeeChannel._
        0 * deleteAllEmployeesChannel._
    }

    def "Should delete all"() {
        when:
        employeeService.deleteAll()

        then:
        0 * addEmployeeChannel._
        0 * deleteEmployeeChannel._
        1 * employeeRepository.deleteAll()
        1 * deleteAllEmployeesChannel.send(_ as Message) >> { Message message ->
            assert message.payload == 'Delete them all'

            true
        }
    }
}
