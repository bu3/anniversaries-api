package io.pivotal.anniversaries

import org.apache.tomcat.jni.Local
import spock.lang.Specification

import java.time.LocalDate


class DefaultAnniversaryServiceSpec extends Specification {

    def "Should load data from database"() {
        given:
        def anniversaryDTO = new AnniversaryDTO(1, 'foo', LocalDate.of(2016,1,1))
        AnniversaryRepository repository = Mock(AnniversaryRepository)
        AnniversaryService anniversaryService = new DefaultAnniversaryService(repository)

        when:
        def anniversaries = anniversaryService.loadAnniversaries()

        then:
        1 * repository.findAll() >> [anniversaryDTO]
        anniversaries[0].id == anniversaryDTO.id
        anniversaries[0].name == anniversaryDTO.name
        anniversaries[0].hireDate == anniversaryDTO.hireDate
        anniversaries[0].anniversaryDate == LocalDate.of(2018, 1, 1)
    }
}
