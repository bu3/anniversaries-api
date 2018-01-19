package io.github.bu3.anniversaries

import spock.lang.Specification

import java.time.LocalDate


class AnniversaryControllerSpec extends Specification {

    def "Should load anniversaries"() {
        given:
        def expectedAnniversaries = new Anniversary(1, 1, 'name', LocalDate.MIN, LocalDate.MAX, "photo url")
        AnniversaryService anniversaryService = Mock(AnniversaryService)
        AnniversaryController anniversaryController = new AnniversaryController(anniversaryService)

        when:
        def anniversaries = anniversaryController.getAnniversaries(null)

        then:
        1 * anniversaryService.loadAnniversaries() >> [expectedAnniversaries]
        anniversaries == [expectedAnniversaries]
    }
}
