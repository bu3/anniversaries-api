package io.github.bu3.employees

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class EmployeeControllerTest extends Specification {

    def employeeService
    def employeeController

    void setup() {
        employeeService = Mock(EmployeeService)
        employeeController = new EmployeeController(employeeService)
    }

    def "Should add a new employee"() {
        given:
        def employee = new Employee(null, 'Foo', "photo url", LocalDate.now())
        def expectedEmployee = new Employee(1, 'Foo', "photo url", LocalDate.now())

        when:
        def result = employeeController.addEmployee(employee)

        then:
        1 * employeeService.store(employee) >> expectedEmployee
        result == expectedEmployee
    }

    def "Should return a list of employees"() {
        given:
        def employees = [new Employee(1, 'Foo', "photo url", LocalDate.MIN)]

        when:
        def response = employeeController.loadEmployees()

        then:
        1 * employeeService.loadEmployees() >> employees
        response == employees
    }

    def "Should delete an employee"() {
        when:
        employeeController.deleteEmployee(1)

        then:
        1 * employeeService.delete(1)
    }

    def "Should delete all employees"() {
        when:
        employeeController.deleteAllEmployees()

        then:
        1 * employeeService.deleteAll()
    }

    @Unroll("#type")
    def "Should validate request"() {
        given:
        def mockEmployeeService = Mock(EmployeeService)
        def employeeController = new EmployeeController(mockEmployeeService)
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
