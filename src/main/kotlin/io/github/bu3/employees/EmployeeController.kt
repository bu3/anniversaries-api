package io.github.bu3.employees

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.DELETE
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.websocket.server.PathParam

@CrossOrigin(origins = arrayOf("*"), methods = arrayOf(POST, DELETE), allowedHeaders = arrayOf("*"))
@RestController
@RequestMapping("/employees")
class EmployeeController(val employeeService: EmployeeService) {

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    fun addEmployee(@Valid @RequestBody employee: Employee): Employee {
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