package io.pivotal.anniversaries.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnniversariesIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    def "Should return anniversaries"() {

        given:
        def anniversariesDates = []
        def expectedDates = []
        1.step 31, 1, {
            expectedDates.add(LocalDate.parse('2016-11-01').plusYears(it).toString())
        }

        when:
        def response = restTemplate.postForEntity('/employees', [name: 'Foo', hireDate: '2016-11-01'], String, [])

        then:
        response.statusCode == HttpStatus.CREATED

        when:
        response = restTemplate.getForEntity('/anniversaries', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 30
        response.body.each { anniversary ->
            assert anniversary.id != null
            assert anniversary.name == 'Foo'
            assert anniversary.hireDate == '2016-11-01'
            anniversariesDates.add(anniversary.anniversaryDate)
        }
        expectedDates.size() == anniversariesDates.size()
        expectedDates.containsAll(anniversariesDates)
    }
}
