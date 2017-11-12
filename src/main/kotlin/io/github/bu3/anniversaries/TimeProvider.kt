package io.github.bu3.anniversaries

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Clock


interface TimeProvider {
    fun now(): LocalDate
}

@Service
class DefaultTimeProvider(private val clock: Clock) : TimeProvider {
    override fun now(): LocalDate = LocalDate.now(clock)
}