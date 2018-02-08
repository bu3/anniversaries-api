package io.github.bu3.events

import io.github.bu3.employees.Employee
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import spock.lang.Specification

import java.time.LocalDate

class EventServiceSpec extends Specification {

    MessageChannel addEmployeeChannel
    MessageChannel deleteEmployeeChannel
    MessageChannel deleteAllEmployeesChannel
    EventRepository eventRepository
    EventService service

    void setup() {
        addEmployeeChannel = Mock(MessageChannel)
        deleteEmployeeChannel = Mock(MessageChannel)
        deleteAllEmployeesChannel = Mock(MessageChannel)
        eventRepository = Mock(EventRepository)
        service = new DefaultEventService(eventRepository, addEmployeeChannel, deleteEmployeeChannel, deleteAllEmployeesChannel)
    }

    def 'Should save a create employee event and trigger an event'() {
        given:
        Employee employee = new Employee("1", 'John Doe', 'photoURL', LocalDate.now())

        when:
        service.createEmployee(employee)

        then:
        1 * eventRepository.save(_ as Aggregate) >> { Aggregate aggregate ->
            assert aggregate.name == employee.name
            assert aggregate.hireDate == employee.hireDate
            assert aggregate.photoURL == employee.photoURL

            aggregate.id = 333

            new Aggregate('randomId', employee.name, employee.photoURL, employee.hireDate)
        }

        1 * addEmployeeChannel.send(_ as Message) >> { Message message ->
            def aggregate = message.payload
            assert aggregate.id == 'randomId'
            assert aggregate.name == employee.name
            assert aggregate.hireDate == employee.hireDate
            assert aggregate.photoURL == employee.photoURL

            true
        }

        0 * deleteEmployeeChannel._
        0 * deleteAllEmployeesChannel._
    }

    def "Should store a delete employee event and trigger an event"() {
        given:
        Employee employee = new Employee("1", 'John Doe', 'photoURL', LocalDate.now())

        when:
        service.deleteEmployee(employee)

        then:
        1 * eventRepository.save(_ as Aggregate) >> { Aggregate aggregate ->
            assert aggregate.name == employee.name
            assert aggregate.hireDate == employee.hireDate
            assert aggregate.photoURL == employee.photoURL

            aggregate.id = 333

            new Aggregate('randomId', employee.name, employee.photoURL, employee.hireDate)
        }

        1 * deleteEmployeeChannel.send(_ as Message) >> { Message message ->
            def aggregate = message.payload
            assert aggregate.id == 'randomId'
            assert aggregate.name == employee.name
            assert aggregate.hireDate == employee.hireDate
            assert aggregate.photoURL == employee.photoURL

            true
        }

        0 * addEmployeeChannel._
        0 * deleteAllEmployeesChannel._
    }

    def "Should trigger a delete all employee event"() {
        when:
        service.deleteAllEmployees()

        then:

        1 * deleteAllEmployeesChannel.send(_ as Message) >> true

        0 * eventRepository._
        0 * addEmployeeChannel._
        0 * deleteEmployeeChannel._
    }
}
