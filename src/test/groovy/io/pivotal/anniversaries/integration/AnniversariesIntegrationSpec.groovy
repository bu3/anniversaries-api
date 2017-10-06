package io.pivotal.anniversaries.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD

@ActiveProfiles("test")
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnniversariesIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    void setup() {
        def response = restTemplate.postForEntity('/employees', [name: 'Foo', hireDate: '2016-11-01'], String, [])
        assert response.statusCode == HttpStatus.CREATED
    }

    def "Should return all anniversaries"() {
        given:
        def expectedDates = []
        1.step 31, 1, {
            expectedDates.add(LocalDate.parse('2016-11-01').plusYears(it).toString())
        }

        when:
        def response = restTemplate.getForEntity('/anniversaries', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 30
        response.body.each { anniversary ->
            assert anniversary.id != null
            assert anniversary.name == 'Foo'
            assert anniversary.hireDate == '2016-11-01'
            expectedDates.contains(anniversary.anniversaryDate)
        }
    }

    def "Should return anniversaries in a time interval"() {
        when:
        def response = restTemplate.getForEntity('/anniversaries?months={months}', List, ['months' : '2'])

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 1
        response.body.each { anniversary ->
            assert anniversary.id != null
            assert anniversary.name == 'Foo'
            assert anniversary.hireDate == '2016-11-01'
            assert anniversary.anniversaryDate == '2017-11-01'
        }
    }
}
