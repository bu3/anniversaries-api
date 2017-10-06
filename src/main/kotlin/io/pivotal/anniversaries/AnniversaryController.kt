package io.pivotal.anniversaries

import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("*"), methods = arrayOf(RequestMethod.GET), allowedHeaders = arrayOf("*"))
@RestController
class AnniversaryController(val anniversaryService: AnniversaryService) {

    @ResponseBody
    @GetMapping("/anniversaries")
    fun getAnniversaries(@RequestParam( value="months", required = false) months: Number?): List<Anniversary> {
        return if(months == null){
            anniversaryService.loadAnniversaries()
        } else{
            anniversaryService.loadAnniversariesWithinMonths(3)
        }
    }

}
