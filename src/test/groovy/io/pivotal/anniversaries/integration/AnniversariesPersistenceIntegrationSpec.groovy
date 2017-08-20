package io.pivotal.anniversaries.integration

import io.pivotal.anniversaries.AnniversaryDTO
import io.pivotal.anniversaries.AnniversaryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnniversariesPersistenceIntegrationSpec extends Specification{

    @Autowired
    AnniversaryRepository repository

    void setup() {
        repository.deleteAll()
    }

    void cleanup() {
        repository.deleteAll()
    }

    def "Should save and load records"() {
        given:
        def anniversary = new AnniversaryDTO(null, 'Foo', LocalDate.MAX)

        when:
        repository.save(anniversary)

        then:
        def records = repository.findAll()
        records.size() == 1
        records[0] == anniversary
    }
}
