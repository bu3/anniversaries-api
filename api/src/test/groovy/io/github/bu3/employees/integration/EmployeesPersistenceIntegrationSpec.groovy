package io.github.bu3.employees.integration

import io.github.bu3.employees.Employee
import io.github.bu3.employees.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeesPersistenceIntegrationSpec extends Specification {

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
        def employee = new Employee("random id", 'Foo', "http://url.com", LocalDate.now())

        when:
        repository.save(employee)

        then:
        def records = repository.findAll()
        records.size() == 1
        records[0] == employee
    }
}
