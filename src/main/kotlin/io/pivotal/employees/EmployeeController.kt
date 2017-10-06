package io.pivotal.employees

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.DELETE
import org.springframework.web.bind.annotation.RequestMethod.POST
import javax.websocket.server.PathParam

@CrossOrigin(origins = arrayOf("*"), methods = arrayOf(POST, DELETE), allowedHeaders = arrayOf("*"))
@RestController
@RequestMapping("/employees")
class EmployeeController(val employeeService: EmployeeService) {

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    fun addEmployee(@RequestBody employee: Employee): Employee {
        return employeeService.store(employee)
    }

    @GetMapping
    fun loadEmployees(): List<Employee> {
        return employeeService.loadEmployees()
    }

    @DeleteMapping("/{employee}")
    @ResponseStatus(NO_CONTENT)
    fun deleteEmployee(@PathParam("employee") employee : Employee) {
        employeeService.delete(employee)
    }
}