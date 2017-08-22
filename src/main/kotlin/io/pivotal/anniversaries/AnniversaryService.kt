package io.pivotal.anniversaries

import org.springframework.stereotype.Service


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


}

