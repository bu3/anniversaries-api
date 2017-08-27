package io.pivotal.anniversaries.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnniversariesIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    def "Should return anniversaries"() {
        when:
        def response = restTemplate.postForEntity('/employees', [name: 'Foo', hireDate: '2017-01-01'], String, [])

        then:
        response.statusCode == HttpStatus.CREATED

        when:
        response = restTemplate.postForEntity('/employees', [name: 'Bar', hireDate: '2015-01-01'], String, [])

        then:
        response.statusCode == HttpStatus.CREATED

        when:
        response = restTemplate.getForEntity('/anniversaries', List)

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
