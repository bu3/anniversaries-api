package io.github.bu3.anniversaries

import io.github.bu3.events.Aggregate
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Service
import java.time.LocalDate

val INITIAL_ANNIVERSARIES = 29

interface AnniversaryService {
    fun loadAnniversaries(): List<Anniversary>
    fun loadAnniversariesWithinMonths(months: Int): List<Anniversary>
    fun handleEmployeeCreatedEvent(aggregate: Aggregate)
    fun handleEmployeeDeletedEvent(aggregate: Aggregate)
    fun handleAllEmployeesDeletedEvent()
}

@Service
class DefaultAnniversaryService(val anniversaryRepository: AnniversaryRepository, val anniversaryParser: AnniversaryParser) : AnniversaryService {

    override fun loadAnniversaries(): List<Anniversary> {
        return anniversaryRepository.findAllByOrderByAnniversaryDateAsc()
    }

    override fun loadAnniversariesWithinMonths(months: Int): List<Anniversary> {
        return anniversaryRepository.findByAnniversaryDateLessThanOrderByAnniversaryDateAsc(LocalDate.now().plusMonths(months.toLong()))
    }

    @StreamListener("createEmployeeInput")
    override fun handleEmployeeCreatedEvent(aggregate: Aggregate) {
        val firstAnniversary = anniversaryParser.calculateAnniversaryDate(aggregate.hireDate)
        val anniversaries = arrayListOf<Anniversary>()
        (0..INITIAL_ANNIVERSARIES step 1).forEach { index ->
            anniversaries.add(Anniversary(name = aggregate.name, employeeId = aggregate.id!!, hireDate = aggregate.hireDate, anniversaryDate = firstAnniversary.plusYears(index.toLong()), photoURL = aggregate.photoURL))
        }
        anniversaryRepository.save(anniversaries)
    }

    @StreamListener("deleteEmployeeInput")
    override fun handleEmployeeDeletedEvent(aggregate: Aggregate) {
        anniversaryRepository.deleteByEmployeeId(aggregate.id!!)
    }

    @StreamListener("deleteAllEmployeesInput")
    override fun handleAllEmployeesDeletedEvent() {
        anniversaryRepository.deleteAll()
    }
}
