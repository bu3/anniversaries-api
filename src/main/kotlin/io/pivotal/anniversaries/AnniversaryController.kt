package io.pivotal.anniversaries

import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("*"), methods = arrayOf(RequestMethod.GET), allowedHeaders = arrayOf("*"))
@RestController
class AnniversaryController (val anniversaryService:AnniversaryService) {

    @ResponseBody
    @GetMapping("/anniversaries")
    fun getAnniversaries(): List<Anniversary> {
        return anniversaryService.loadAnniversaries()
    }

}
