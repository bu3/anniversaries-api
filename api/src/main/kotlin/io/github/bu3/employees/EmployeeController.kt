package io.github.bu3.employees

import io.github.bu3.events.EventService
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.DELETE
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@CrossOrigin(origins = arrayOf("*"), methods = arrayOf(POST, DELETE), allowedHeaders = arrayOf("*"))
@RestController
@RequestMapping("/employees")
class EmployeeController(private val employeeService: EmployeeService, private val eventService: EventService) {

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    fun addEmployee(@Valid @RequestBody employee: Employee) {
        eventService.createEmployee(employee)
    }

    @GetMapping
    fun loadEmployees(): List<Employee> {
        return employeeService.loadEmployees()
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    fun deleteEmployee(@PathVariable("id") id: String) {
        val employee = employeeService.findById(id)
        eventService.deleteEmployee(employee)
    }

    @Profile("test")
    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun deleteAllEmployees() {
        eventService.deleteAllEmployees()
    }
}