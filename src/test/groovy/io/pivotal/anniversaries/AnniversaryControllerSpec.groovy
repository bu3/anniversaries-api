package io.pivotal.anniversaries

import spock.lang.Specification


class AnniversaryControllerSpec extends Specification {

    def "Should load anniversaries"() {
        given:
        def expectedAnniversaries = new Anniversary(1, 'name', 'aHiringDate')
        AnniversaryService anniversaryService = Mock(AnniversaryService)
        AnniversaryController anniversaryController = new AnniversaryController(anniversaryService)

        when:
        def anniversaries = anniversaryController.getAnniversaries()

        then:
        anniversaryService.loadAnniversaries() >> [expectedAnniversaries]
        anniversaries == [expectedAnniversaries]
    }
}
