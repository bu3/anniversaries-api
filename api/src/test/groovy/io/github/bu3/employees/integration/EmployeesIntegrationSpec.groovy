package io.github.bu3.employees.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.http.HttpMethod.DELETE

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeesIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    void cleanup() {
        restTemplate.delete('/employees')
    }

    def "Should expose employee API"() {
        when:
        def response = restTemplate.postForEntity('/employees', [name: 'Foo', hireDate: '2016-11-01', photoURL: 'http://cool.photo.jpg'], Map, [])

        then:
        response.statusCode == HttpStatus.CREATED
        Thread.sleep(2000)

        when:
        response = restTemplate.getForEntity('/employees', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 1

        with(response.body[0]) {
            assert id != null
            assert name == 'Foo'
            assert hireDate == '2016-11-01'
            assert photoURL == 'http://cool.photo.jpg'
        }

        when:
        response = restTemplate.exchange("/employees/${response.body[0].id}", DELETE, null, String)

        then:
        response.statusCode == HttpStatus.NO_CONTENT
        Thread.sleep(2000)

        when:
        response = restTemplate.getForEntity('/employees', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 0
    }
}
