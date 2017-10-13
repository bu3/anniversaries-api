package io.github.bu3.anniversaries

import spock.lang.Specification

import java.time.LocalDate

import static io.github.bu3.anniversaries.AnniversaryParserKt.calculateAnniversaryDate


class AnniversaryParserKtTest extends Specification {

    def "Calculate anniversary date for leap years"() {
        when:
        def anniversaryDate = calculateAnniversaryDate(LocalDate.of(2008, 2, 29))

        then:
        anniversaryDate == LocalDate.of(2018, 2, 28)
    }
}
