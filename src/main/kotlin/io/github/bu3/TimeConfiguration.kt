package io.github.bu3

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@Configuration
class TimeConfiguration {

    @Profile("!test")
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()

    @Profile("test")
    @Bean
    fun fixedClock(): Clock = Clock.fixed(Instant.parse("2017-10-01T10:15:30.00Z"), ZoneId.of("UTC"))
}