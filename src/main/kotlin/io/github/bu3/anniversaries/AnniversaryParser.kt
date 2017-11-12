package io.github.bu3.anniversaries

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Month

interface AnniversaryParser {
    fun calculateAnniversaryDate(hiringDate: LocalDate): LocalDate
}

@Service
class DefaultAnniversaryParser(val timeProvider: TimeProvider): AnniversaryParser {

    override fun calculateAnniversaryDate(hiringDate: LocalDate): LocalDate {
        val today = timeProvider.now()
        val anniversaryDate = manageLeapYears(hiringDate, today)
        return if (anniversaryDate.isBefore(today)) {
            anniversaryDate.plusYears(1)
        } else
            anniversaryDate
    }

    private fun manageLeapYears(hiringDate: LocalDate, today: LocalDate): LocalDate {
        return if (isLastDayOfFebruaryInLeapYear(hiringDate)) {
            val month = hiringDate.month
            val year = today.year
            val dayOfYear = if (today.isLeapYear) {
                hiringDate.dayOfYear
            } else {
                hiringDate.dayOfMonth - 1
            }

            LocalDate.of(year, month, dayOfYear)
        } else {
            LocalDate.of(today.year, hiringDate.month, hiringDate.dayOfMonth)
        }
    }

    private fun isLastDayOfFebruaryInLeapYear(date: LocalDate): Boolean {
        return date.isLeapYear && date.month == Month.FEBRUARY && date.dayOfMonth == 29
    }
}


