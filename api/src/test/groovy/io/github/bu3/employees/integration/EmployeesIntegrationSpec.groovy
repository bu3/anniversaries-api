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
        def employee

        when:
        def response = restTemplate.postForEntity('/employees', [name: 'Foo', hireDate: '2016-11-01', photoURL: 'http://cool.photo.jpg'], Map, [])

        then:
        response.statusCode == HttpStatus.CREATED

        when:
        employee = response.body
        response = restTemplate.getForEntity('/employees', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 1

        with(response.body[0]) {
            assert id == employee.id
            assert name == employee.name
            assert hireDate == employee.hireDate
            assert photoURL == employee.photoURL
        }

        when:
        response = restTemplate.exchange("/employees/${employee.id}", DELETE, null, String)

        then:
        response.statusCode == HttpStatus.NO_CONTENT

        when:
        response = restTemplate.getForEntity('/employees', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 0
    }
}
