package io.github.bu3.anniversaries

import io.github.bu3.events.Aggregate
import spock.lang.Specification

import java.time.LocalDate

class DefaultAnniversaryServiceSpec extends Specification {

    def anniversaryParser
    def anniversaryService
    def anniversaryRepository

    void setup() {
        anniversaryParser = Mock(AnniversaryParser)
        anniversaryRepository = Mock(AnniversaryRepository)
        anniversaryService = new DefaultAnniversaryService(anniversaryRepository, anniversaryParser)
    }

    def "Should load data from database"() {
        given:
        def expectedAnniversaries = [new Anniversary(1, "1", 'foo', LocalDate.MIN, LocalDate.MAX, 'photo url')]

        when:
        def anniversaries = anniversaryService.loadAnniversaries()

        then:
        1 * anniversaryRepository.findAllByOrderByAnniversaryDateAsc() >> expectedAnniversaries
        anniversaries == expectedAnniversaries
    }

    def "Should load data from database by time interval"() {
        given:
        def expectedAnniversaries = [new Anniversary(1, "2", 'foo', LocalDate.MIN, LocalDate.MAX, 'photo url')]

        when:
        def anniversaries = anniversaryService.loadAnniversariesWithinMonths(3)

        then:
        1 * anniversaryRepository.findByAnniversaryDateLessThanOrderByAnniversaryDateAsc(LocalDate.now().plusMonths(3)) >> expectedAnniversaries
        anniversaries == expectedAnniversaries
    }

    def "Should manage an employee created event"() {
        given:
        def anniversaryDate = LocalDate.of(2018, 11, 1)
        def aggregate = new Aggregate("1", 'Foo', 'photo url', LocalDate.of(2017, 11, 1))

        when:
        anniversaryService.handleEmployeeCreatedEvent(aggregate)

        then:
        1 * anniversaryParser.calculateAnniversaryDate(aggregate.hireDate) >> anniversaryDate
        1 * anniversaryRepository.save(_ as List<Anniversary>) >> { args ->
            def anniversaries = args[0]
            anniversaries.size() == 30
            anniversaries.eachWithIndex { anniversary, index ->
                assert anniversary.name == aggregate.name
                assert anniversary.employeeId == "1"
                assert anniversary.hireDate == aggregate.hireDate
                assert anniversary.photoURL == aggregate.photoURL
                assert anniversary.anniversaryDate == anniversaryDate.plusYears(index)
            }
        }
    }

    def "Should delete anniversaries when an employee gets deleted"() {
        given:
        def aggregate = new Aggregate("109", 'Foo', 'photo url', LocalDate.now())

        when:
        anniversaryService.handleEmployeeDeletedEvent(aggregate)

        then:
        1 * anniversaryRepository.deleteByEmployeeId("109")
    }

    def "Should delete all anniversaries when all employees get deleted"() {
        when:
        anniversaryService.handleAllEmployeesDeletedEvent()

        then:
        1 * anniversaryRepository.deleteAll()
    }
}
