package io.pivotal.anniversaries

import io.pivotal.employees.EmployeeCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service


interface AnniversaryService {
    fun loadAnniversaries(): List<Anniversary>
    fun handleEmployeeCreatedEvent(employeeCreatedEvent: EmployeeCreatedEvent)
}

@Service
class DefaultAnniversaryService(val anniversaryRepository: AnniversaryRepository): AnniversaryService  {

    override fun loadAnniversaries(): List<Anniversary> {
        return anniversaryRepository.findAllByOrderByAnniversaryDateAsc()
    }

    @EventListener
    override fun handleEmployeeCreatedEvent(employeeCreatedEvent: EmployeeCreatedEvent) {
        val employee = employeeCreatedEvent.employee
        val anniversary = Anniversary(employee.id, employee.name, employee.hireDate, calculateAnniversaryDate(employee.hireDate))
        anniversaryRepository.save(anniversary)
    }
}
