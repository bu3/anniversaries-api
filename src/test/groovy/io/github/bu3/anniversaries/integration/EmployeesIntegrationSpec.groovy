package io.github.bu3.anniversaries.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD

@ActiveProfiles("test")
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeesIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    def "Should expose employee API"() {
        def employee = [name: 'Foo', hireDate: '2016-11-01', photoURL: 'http://cool.photo.jpg']

        when:
        def response = restTemplate.postForEntity('/employees', employee, String, [])

        then:
        response.statusCode == HttpStatus.CREATED

        when:
        response = restTemplate.getForEntity('/employees', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 1

        with(response.body[0]) {
            assert id == 1
            assert name == employee.name
            assert hireDate == employee.hireDate
            assert photoURL == employee.photoURL
        }

        when:
        response = restTemplate.exchange('/employees/1', HttpMethod.DELETE, new HttpEntity<Object>(""), String)

        then:
        response.statusCode == HttpStatus.NO_CONTENT

        when:
        response = restTemplate.getForEntity('/employees', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 0
    }
}
