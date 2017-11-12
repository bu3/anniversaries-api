package io.github.bu3.anniversaries

import spock.lang.Specification

import java.time.LocalDate

class DefaultAnniversaryParserKtTest extends Specification {

    def "Calculate anniversary date for leap years"() {
        given:
        def timeProvider = Mock(TimeProvider)
        DefaultAnniversaryParser defaultAnniversaryParser = new DefaultAnniversaryParser(timeProvider)

        when:
        def anniversaryDate = defaultAnniversaryParser.calculateAnniversaryDate(LocalDate.of(2008, 2, 29))

        then:
        1 * timeProvider.now() >> LocalDate.of(2017, 10, 1)
        anniversaryDate == LocalDate.of(2018, 2, 28)
    }
}
