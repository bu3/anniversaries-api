package io.github.bu3.events

import io.github.bu3.employees.Employee
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

interface EventService {
    fun createEmployee(employee: Employee)
    fun deleteEmployee(employee: Employee)
    fun deleteAllEmployees()
}

@Service
class DefaultEventService(
    private val eventRepository:EventRepository,
    @Qualifier("createEmployeeOutput") val addEmployeeChannel: MessageChannel,
    @Qualifier("deleteEmployeeOutput") val deleteEmployeeChannel: MessageChannel,
    @Qualifier("deleteAllEmployeesOutput") val deleteAllEmployeesChannel: MessageChannel
): EventService {

    override fun createEmployee(employee: Employee) {
        val aggregate = Aggregate(name = employee.name, photoURL = employee.photoURL, hireDate = employee.hireDate)
        val savedAggregate = eventRepository.save(aggregate)
        addEmployeeChannel.send(MessageBuilder.withPayload(savedAggregate).build())
    }

    override fun deleteEmployee(employee: Employee) {
        val aggregate = Aggregate(id = employee.id, name = employee.name, photoURL = employee.photoURL, hireDate = employee.hireDate)
        val savedAggregate = eventRepository.save(aggregate)
        deleteEmployeeChannel.send(MessageBuilder.withPayload(savedAggregate).build())
    }

    override fun deleteAllEmployees() {
        deleteAllEmployeesChannel.send(MessageBuilder.withPayload("Delete them all").build())
    }
}