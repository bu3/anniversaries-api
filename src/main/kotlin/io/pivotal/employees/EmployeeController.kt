package io.pivotal.employees

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("*"), methods = arrayOf(RequestMethod.POST), allowedHeaders = arrayOf("*"))
@RestController
class EmployeeController(private val employeeService: EmployeeService) {

    @PostMapping("/employees")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun addEmployee(@RequestBody employee: Employee): Employee {
        return employeeService.store(employee)
    }
}