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
        publisher.publishEvent(EmployeeCreatedEvent(Aggregate(save.id, save.name, save.photoURL, save.hireDate)))
        return save
    }

    override fun loadEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }

    override fun delete(employeeId: Long) {
        val employee = employeeRepository.findOne(employeeId)
        employeeRepository.delete(employeeId)
        if (employee != null) {
            publisher.publishEvent(EmployeeDeletedEvent(Aggregate(employee.id, employee.name, employee.photoURL, employee.hireDate)))
        }
    }

    override fun deleteAll() {
        employeeRepository.deleteAll()
        publisher.publishEvent(AllEmployeeDeletedEvent())
    }
}