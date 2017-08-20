package io.pivotal.anniversaries

import org.springframework.stereotype.Service


interface AnniversaryService {
    fun loadAnniversaries(): List<Anniversary>
}

@Service
class DefaultAnniversaryService(val anniversaryRepository: AnniversaryRepository): AnniversaryService  {

    override fun loadAnniversaries(): List<Anniversary> {
        return anniversaryRepository.findAll()
    }
}

