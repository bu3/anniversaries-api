package io.pivotal.anniversaries

import io.pivotal.employees.Employee
import io.pivotal.employees.EmployeeCreatedEvent
import spock.lang.Specification

import java.time.LocalDate

class DefaultAnniversaryServiceSpec extends Specification {

    def anniversaryService
    def anniversaryRepository

    void setup() {
        anniversaryRepository = Mock(AnniversaryRepository)
        anniversaryService = new DefaultAnniversaryService(anniversaryRepository)
    }

    def "Should load data from database"() {
        given:
        def expectedAnniversaries = [new Anniversary(1, 'foo', LocalDate.MIN, LocalDate.MAX )]

        when:
        def anniversaries = anniversaryService.loadAnniversaries()

        then:
        1 * anniversaryRepository.findAll() >> expectedAnniversaries
        anniversaries == expectedAnniversaries
    }

    def "Should manage an employee created event"() {
        given:
        def employee = new Employee(1,'Foo', LocalDate.now())
        def employeeCreatedEvent = new EmployeeCreatedEvent(employee)

        when:
        anniversaryService.handleEmployeeCreatedEvent(employeeCreatedEvent)

        then:
        1 * anniversaryRepository.save(_ as Anniversary) >> { args ->
            def anniversary = args[0]
            anniversary.name == employee.name
            anniversary.hireDate == employee.hireDate
            anniversary.anniversaryDate != null
        }

    }
}
