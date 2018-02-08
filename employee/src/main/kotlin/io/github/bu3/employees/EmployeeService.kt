package io.github.bu3.employees

import io.github.bu3.events.Aggregate
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Service

interface EmployeeService {
    fun handleEmployeeCreatedEvent(aggregate: Aggregate)
    fun handleEmployeeDeletedEvent(aggregate: Aggregate)
    fun handleAllEmployeesDeletedEvent()
    fun loadEmployees(): List<Employee>
    fun findById(id:String): Employee
}

@Service
class DefaultEmployeeService(val employeeRepository: EmployeeRepository) : EmployeeService {

    @StreamListener("createEmployeeInput")
    override fun handleEmployeeCreatedEvent(aggregate: Aggregate) {
        val employee = Employee(aggregate.id, aggregate.name, aggregate.photoURL, aggregate.hireDate)
        employeeRepository.save(employee)
    }

    override fun loadEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }

    @StreamListener("deleteEmployeeInput")
    override fun handleEmployeeDeletedEvent(aggregate: Aggregate) {
        employeeRepository.delete(aggregate.id)
    }

    @StreamListener("deleteAllEmployeesInput")
    override fun handleAllEmployeesDeletedEvent() {
        employeeRepository.deleteAll()
    }

    override fun findById(id: String): Employee {
        return employeeRepository.findOne(id)
    }
}