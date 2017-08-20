package io.pivotal.anniversaries

import org.springframework.stereotype.Service
import java.time.LocalDate


interface AnniversaryService {
    fun loadAnniversaries(): List<Anniversary>
}

@Service
class DefaultAnniversaryService(val anniversaryRepository: AnniversaryRepository): AnniversaryService  {

    override fun loadAnniversaries(): List<Anniversary> {
        val anniversaries = anniversaryRepository.findAll()
        return anniversaries.map {
            val anniversary = Anniversary(it.id, it.name, it.hireDate)
            anniversary.anniversaryDate = calculateAnniversaryDate(it.hireDate)

            anniversary
        }
    }

    private fun calculateAnniversaryDate(hiringDate: LocalDate): LocalDate {
        val today = LocalDate.now()
        val anniversaryDate = LocalDate.of(today.year, hiringDate.month, hiringDate.dayOfYear)
        if (anniversaryDate.isBefore(today) ) {
            return anniversaryDate.plusYears(1)
        }
        else
            return anniversaryDate
    }
}

