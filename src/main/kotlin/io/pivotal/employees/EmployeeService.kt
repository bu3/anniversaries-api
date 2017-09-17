package io.pivotal.employees

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

interface EmployeeService {
    fun store(employee: Employee): Employee
    fun loadEmployees(): List<Employee>
}

@Service
class DefaultEmployeeService(val employeeRepository: EmployeeRepository, val publisher: ApplicationEventPublisher) : EmployeeService {
    override fun store(employee: Employee): Employee {
        val save = employeeRepository.save(employee)
        publisher.publishEvent(EmployeeCreatedEvent(save))
        return save
    }

    override fun loadEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }
}