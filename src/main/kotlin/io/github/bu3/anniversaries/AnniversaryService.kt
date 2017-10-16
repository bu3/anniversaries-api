package io.github.bu3.anniversaries

import io.github.bu3.employees.EmployeeCreatedEvent
import io.github.bu3.employees.EmployeeDeletedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.time.LocalDate

val INITIAL_ANNIVERSARIES = 29

interface AnniversaryService {
    fun loadAnniversaries(): List<Anniversary>
    fun loadAnniversariesWithinMonths(months:Int): List<Anniversary>
    fun handleEmployeeCreatedEvent(employeeCreatedEvent: EmployeeCreatedEvent)
    fun handleEmployeeDeletedEvent(employeeDeletedEvent: EmployeeDeletedEvent)
}

@Service
class DefaultAnniversaryService(val anniversaryRepository: AnniversaryRepository) : AnniversaryService {

    override fun loadAnniversaries(): List<Anniversary> {
        return anniversaryRepository.findAllByOrderByAnniversaryDateAsc()
    }

    override fun loadAnniversariesWithinMonths(months: Int): List<Anniversary> {
        return anniversaryRepository.findByAnniversaryDateLessThanOrderByAnniversaryDateAsc(LocalDate.now().plusMonths(months.toLong()))
    }

    @EventListener
    override fun handleEmployeeCreatedEvent(employeeCreatedEvent: EmployeeCreatedEvent) {
        val employee = employeeCreatedEvent.employee
        val firstAnniversary = calculateAnniversaryDate(employee.hireDate)
        val anniversaries = arrayListOf<Anniversary>()
        (0..INITIAL_ANNIVERSARIES step 1).forEach { index ->
            anniversaries.add(Anniversary(name = employee.name, employeeId = employee.id!!, hireDate = employee.hireDate, anniversaryDate = firstAnniversary.plusYears(index.toLong()), photoURL = employee.photoURL))
        }
        anniversaryRepository.save(anniversaries)
    }

    @EventListener
    override fun handleEmployeeDeletedEvent(employeeDeletedEvent: EmployeeDeletedEvent) {
        anniversaryRepository.deleteByEmployeeId(employeeDeletedEvent.employee.id!!)
    }
}
