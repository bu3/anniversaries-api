package io.github.bu3.anniversaries.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.http.HttpStatus.*

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnniversariesIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    void setup() {
        def response = restTemplate.postForEntity('/employees', [name: 'Foo', hireDate: '2016-11-01', photoURL: 'http://cool.photo.jpg'], Map, [])
        assert response.statusCode == CREATED
        Thread.sleep(2000)
    }

    void cleanup() {
        restTemplate.delete('/employees')
        Thread.sleep(2000)
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
        response.statusCode == OK
        response.body.size() == 30
        response.body.each { anniversary ->
            assert anniversary.id != null
            assert anniversary.employeeId != null
            assert anniversary.name == 'Foo'
            assert anniversary.hireDate == '2016-11-01'
            assert anniversary.photoURL == 'http://cool.photo.jpg'
            expectedDates.contains(anniversary.anniversaryDate)
        }
    }

    def "Should return anniversaries in a time interval"() {
        when:
        def response = restTemplate.getForEntity('/anniversaries?months={months}', List, ['months': '2'])

        then:
        response.statusCode == OK
        response.body.size() == 1
        def anniversary = response.body[0]
        anniversary.id != null
        anniversary.employeeId != null
        anniversary.name == 'Foo'
        anniversary.hireDate == '2016-11-01'
        anniversary.photoURL == 'http://cool.photo.jpg'
        anniversary.anniversaryDate == '2017-11-01'
    }

    def "Should delete anniversaries if an employee gets deleted"() {
        when:
        def response = restTemplate.getForEntity('/anniversaries?months={months}', List, ['months': '2'])

        then:
        response.statusCode == OK
        response.body.size() == 1

        when:
        response = restTemplate.exchange("/employees/${response.body[0].employeeId}", HttpMethod.DELETE, new HttpEntity<Object>(""), String)

        then:
        response.statusCode == NO_CONTENT

        when:
        Thread.sleep(2000)
        response = restTemplate.getForEntity('/anniversaries?months={months}', List, ['months': '2'])

        then:
        response.statusCode == OK
        response.body.size() == 0
    }
}
