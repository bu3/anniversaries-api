package io.pivotal.employees

import org.springframework.stereotype.Service

interface EmployeeService {
    fun store(employee: Employee): Employee
    fun loadEmployees(): List<Employee>
}

@Service
class DefaultEmployeeService(val employeeRepository: EmployeeRepository) : EmployeeService {
    override fun store(employee: Employee): Employee {
        return employeeRepository.save(employee)
    }

    override fun loadEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }
}