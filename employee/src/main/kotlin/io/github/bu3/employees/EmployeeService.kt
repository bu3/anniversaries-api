package io.github.bu3.employees

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.stream.messaging.Source
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Service

interface EmployeeService {
    fun store(employee: Employee): Employee
    fun loadEmployees(): List<Employee>
    fun delete(employeeId: Long)
    fun deleteAll()
}

@Service
class DefaultEmployeeService(
    val employeeRepository: EmployeeRepository,
    @Qualifier("createEmployeeOutput") val addEmployeeChannel: MessageChannel,
    @Qualifier("deleteEmployeeOutput") val deleteEmployeeChannel: MessageChannel,
    @Qualifier("deleteAllEmployeesOutput") val deleteAllEmployeeChannel: MessageChannel
    ) : EmployeeService {

    override fun store(employee: Employee): Employee {
        val save = employeeRepository.save(employee)
        addEmployeeChannel.send(MessageBuilder.withPayload(Aggregate(save.id, save.name, save.photoURL, save.hireDate)).build())
        return save
    }

    override fun loadEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }

    override fun delete(employeeId: Long) {
        val employee = employeeRepository.findOne(employeeId)
        employeeRepository.delete(employeeId)
        if (employee != null) {
            deleteEmployeeChannel.send(MessageBuilder.withPayload(Aggregate(employee.id, employee.name, employee.photoURL, employee.hireDate)).build())
        }
    }

    override fun deleteAll() {
        employeeRepository.deleteAll()
        deleteAllEmployeeChannel.send(MessageBuilder.withPayload("Delete them all").build())
    }
}