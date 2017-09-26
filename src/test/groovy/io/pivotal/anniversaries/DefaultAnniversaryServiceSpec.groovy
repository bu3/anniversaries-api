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
        def expectedAnniversaries = [new Anniversary(1, 'foo', LocalDate.MIN, LocalDate.MAX)]

        when:
        def anniversaries = anniversaryService.loadAnniversaries()

        then:
        1 * anniversaryRepository.findAllByOrderByAnniversaryDateAsc() >> expectedAnniversaries
        anniversaries == expectedAnniversaries
    }

    def "Should load data from database by time interval"() {
        given:
        def expectedAnniversaries = [new Anniversary(1, 'foo', LocalDate.MIN, LocalDate.MAX)]

        when:
        def anniversaries = anniversaryService.loadAnniversariesWithinMonths(3)

        then:
        1 * anniversaryRepository.findByAnniversaryDateLessThanOrderByAnniversaryDateAsc(LocalDate.now().plusMonths(3)) >> expectedAnniversaries
        anniversaries == expectedAnniversaries
    }

    def "Should manage an employee created event"() {
        given:
        def employee = new Employee(1, 'Foo', LocalDate.now())
        def employeeCreatedEvent = new EmployeeCreatedEvent(employee)

        when:
        anniversaryService.handleEmployeeCreatedEvent(employeeCreatedEvent)

        then:
        1 * anniversaryRepository.save(_ as List<Anniversary>) >> { args ->
            def anniversaries = args[0]
            anniversaries.size() == 30
            anniversaries.each { anniversary ->
                assert anniversary.name == employee.name
                assert anniversary.hireDate == employee.hireDate
                assert anniversary.anniversaryDate != null
            }
        }

    }
}
