package io.pivotal.anniversaries

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AnniversaryController (val anniversaryService:AnniversaryService) {

    @ResponseBody
    @GetMapping("/anniversaries")
    fun getAnniversaries(): List<Anniversary> {
        return anniversaryService.loadAnniversaries()
    }

}
