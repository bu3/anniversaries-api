package io.pivotal.anniversaries

import spock.lang.Specification


class DefaultAnniversaryServiceSpec extends Specification {

    def "Should load data from database"() {
        given:
        def expectedAnniversaries = [new Anniversary(1, 'foo', 'hiringDate')]
        AnniversaryRepository repository = Mock(AnniversaryRepository)
        AnniversaryService anniversaryService = new DefaultAnniversaryService(repository)

        when:
        def anniversaries = anniversaryService.loadAnniversaries()

        then:
        1 * repository.findAll() >> expectedAnniversaries
        anniversaries == expectedAnniversaries
    }
}
