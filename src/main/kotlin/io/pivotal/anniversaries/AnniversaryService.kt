package io.pivotal.anniversaries

import io.pivotal.employees.EmployeeService
import org.springframework.stereotype.Service


interface AnniversaryService {
    fun loadAnniversaries(): List<Anniversary>
}

@Service
class DefaultAnniversaryService(val employeeService: EmployeeService): AnniversaryService  {

    override fun loadAnniversaries(): List<Anniversary> {
        val anniversaries = employeeService.loadEmployees()
        return anniversaries.map {
            val anniversary = Anniversary(it.id, it.name, it.hireDate)
            anniversary.anniversaryDate = calculateAnniversaryDate(it.hireDate)

            anniversary
        }
    }


}

