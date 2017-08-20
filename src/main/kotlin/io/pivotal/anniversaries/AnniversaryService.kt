package io.pivotal.anniversaries

import org.springframework.stereotype.Service


interface AnniversaryService {
    fun loadAnniversaries(): List<Anniversary>
}

@Service
class DefaultAnniversaryService(val anniversaryRepository: AnniversaryRepository): AnniversaryService  {

    override fun loadAnniversaries(): List<Anniversary> {
        val anniversaries = anniversaryRepository.findAll()
        return anniversaries.map { Anniversary(it.id, it.name, it.hireDate) }
    }
}

