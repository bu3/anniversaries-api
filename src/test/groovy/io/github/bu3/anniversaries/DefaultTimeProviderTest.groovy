package io.github.bu3.anniversaries

import spock.lang.Specification

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


class DefaultTimeProviderTest extends Specification {

    def "Should return current date"() {
        given:
        Clock clock = Clock.fixed(Instant.parse('2017-11-01T10:15:30.00Z'), ZoneId.of('UTC'))
        def timeProvider = new DefaultTimeProvider(clock)

        when:
        def now = timeProvider.now()

        then:
        now == LocalDate.now(clock)
    }
}
