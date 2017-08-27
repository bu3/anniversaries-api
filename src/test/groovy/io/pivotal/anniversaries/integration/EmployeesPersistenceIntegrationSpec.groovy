package io.pivotal.anniversaries.integration

import io.pivotal.employees.Employee
import io.pivotal.employees.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeesPersistenceIntegrationSpec extends Specification{

    @Autowired
    EmployeeRepository repository

    void setup() {
        repository.deleteAll()
    }

    void cleanup() {
        repository.deleteAll()
    }

    def "Should save and load records"() {
        given:
        def anniversary = new Employee(null, 'Foo', LocalDate.MAX)

        when:
        repository.save(anniversary)

        then:
        def records = repository.findAll()
        records.size() == 1
        records[0] == anniversary
    }
}
