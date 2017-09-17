package io.pivotal.anniversaries

import spock.lang.Specification

import java.time.LocalDate


class AnniversaryControllerSpec extends Specification {

    def "Should load anniversaries"() {
        given:
        def expectedAnniversaries = new Anniversary(1, 'name', LocalDate.MIN, LocalDate.MAX)
        AnniversaryService anniversaryService = Mock(AnniversaryService)
        AnniversaryController anniversaryController = new AnniversaryController(anniversaryService)

        when:
        def anniversaries = anniversaryController.getAnniversaries()

        then:
        anniversaryService.loadAnniversaries() >> [expectedAnniversaries]
        anniversaries == [expectedAnniversaries]
    }
}
