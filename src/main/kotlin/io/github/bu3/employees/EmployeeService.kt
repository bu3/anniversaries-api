package io.github.bu3.employees

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

interface EmployeeService {
    fun store(employee: Employee): Employee
    fun loadEmployees(): List<Employee>
    fun delete(employeeId: Long)
    fun deleteAll()
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

    override fun delete(employeeId: Long) {
        val employee = employeeRepository.findOne(employeeId)
        employeeRepository.delete(employeeId)
        if (employee != null) {
            publisher.publishEvent(EmployeeDeletedEvent(employee))
        }
    }

    override fun deleteAll() {
        employeeRepository.deleteAll()
        publisher.publishEvent(AllEmployeeDeletedEvent())
    }
}