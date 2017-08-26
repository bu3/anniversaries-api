package io.pivotal.anniversaries.integration

import io.pivotal.anniversaries.Employee
import io.pivotal.anniversaries.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnniversariesIntegrationSpec extends Specification{

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    EmployeeRepository repository

    void setup() {
        repository.save(new Employee(null, 'Foo', LocalDate.parse('2017-01-01')))
        repository.save(new Employee(null, 'Bar', LocalDate.parse('2015-01-01')))
    }

    void cleanup() {
        repository.deleteAll()
    }

    def "Should return anniversaries"() {
        when:
        def response = restTemplate.getForEntity('/anniversaries', List)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 2
        response.body[0].id != null
        response.body[0].name == 'Foo'
        response.body[0].hireDate == '2017-01-01'
        response.body[0].anniversaryDate == '2018-01-01'

        response.body[1].id != null
        response.body[1].name == 'Bar'
        response.body[1].hireDate == '2015-01-01'
        response.body[1].anniversaryDate == '2018-01-01'
    }
}
