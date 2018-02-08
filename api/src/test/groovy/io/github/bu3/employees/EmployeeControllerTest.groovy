package io.github.bu3.employees

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.bu3.events.EventService
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class EmployeeControllerTest extends Specification {

    EmployeeService employeeService
    EventService eventService
    EmployeeController employeeController

    void setup() {
        employeeService = Mock(EmployeeService)
        eventService = Mock(EventService)
        employeeController = new EmployeeController(employeeService, eventService)
    }

    def "Should add a new employee"() {
        given:
        def employee = new Employee(null, 'Foo', "photo url", LocalDate.now())

        when:
        employeeController.addEmployee(employee)

        then:
        0 * employeeService._
        1 * eventService.createEmployee(employee)
    }

    def "Should return a list of employees"() {
        given:
        def employees = [new Employee("1", 'Foo', "photo url", LocalDate.MIN)]

        when:
        def response = employeeController.loadEmployees()

        then:
        1 * employeeService.loadEmployees() >> employees
        response == employees
    }

    def "Should delete an employee"() {
        given:
        def employee = new Employee("1", 'foo', "photo Url", LocalDate.of(2016, 1, 1))

        when:
        employeeController.deleteEmployee("1")

        then:
        1 * employeeService.findById("1") >> employee
        1 * eventService.deleteEmployee(employee)
    }

    def "Should delete all employees"() {
        when:
        employeeController.deleteAllEmployees()

        then:
        0 * employeeService._
        1 * eventService.deleteAllEmployees()
    }

    @Unroll("#type")
    def "Should validate request"() {
        given:
        def mvc = MockMvcBuilders.standaloneSetup(employeeController).build()

        when:
        def request = mvc.perform(
                post("/employees")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(employee)))

        then:
        request.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())

        where:
        employee                                             | type                | _
        [hireDate: '2016-11-01', photoURL: 'http://url.com'] | 'invalid name'      | _
        [name: 'Foo', photoURL: 'http://url.com']            | 'invalid hire date' | _
        [name: 'Foo', hireDate: '2016-11-01', photoURL: "1"] | 'invalid photo url' | _
    }
}
