package io.pivotal.anniversaries

import io.pivotal.employees.EmployeeRepository
import org.springframework.stereotype.Service


interface AnniversaryService {
    fun loadAnniversaries(): List<Anniversary>
}

@Service
class DefaultAnniversaryService(val employeeRepository: EmployeeRepository): AnniversaryService  {

    override fun loadAnniversaries(): List<Anniversary> {
        val anniversaries = employeeRepository.findAll()
        return anniversaries.map {
            val anniversary = Anniversary(it.id, it.name, it.hireDate)
            anniversary.anniversaryDate = calculateAnniversaryDate(it.hireDate)

            anniversary
        }
    }


}

