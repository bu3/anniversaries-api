package io.pivotal.anniversaries

import spock.lang.Specification


class DefaultAnniversaryServiceSpec extends Specification {

    def "Should load data from database"() {
        given:
        def anniversaryDTO = new AnniversaryDTO(1, 'foo', 'hiringDate')
        AnniversaryRepository repository = Mock(AnniversaryRepository)
        AnniversaryService anniversaryService = new DefaultAnniversaryService(repository)

        when:
        def anniversaries = anniversaryService.loadAnniversaries()

        then:
        1 * repository.findAll() >> [anniversaryDTO]
        anniversaries[0].id == anniversaryDTO.id
        anniversaries[0].name == anniversaryDTO.name
        anniversaries[0].hireDate == anniversaryDTO.hireDate
    }
}
