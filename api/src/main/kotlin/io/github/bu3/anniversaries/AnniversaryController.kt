package io.github.bu3.anniversaries

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = arrayOf("*"), methods = arrayOf(RequestMethod.GET), allowedHeaders = arrayOf("*"))
@RestController
class AnniversaryController(val anniversaryService: AnniversaryService) {

    @ResponseBody
    @GetMapping("/anniversaries")
    fun getAnniversaries(@RequestParam(value = "months", required = false) months: Number?): List<Anniversary> {
        return if (months == null) {
            anniversaryService.loadAnniversaries()
        } else {
            anniversaryService.loadAnniversariesWithinMonths(3)
        }
    }
}
