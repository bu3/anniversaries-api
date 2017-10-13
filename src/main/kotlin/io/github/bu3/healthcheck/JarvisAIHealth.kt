package io.github.bu3.healthcheck

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class JarvisAIHealth() : HealthIndicator {

    val restTemplate: RestTemplate = RestTemplate()

    override fun health(): Health {
        try {
            val response = restTemplate.getForEntity("https://us-central1-concourse-setup.cloudfunctions.net/health", String::class.java)
            if (response.statusCode != HttpStatus.OK) {
                return Health.down().build()
            }
            return Health.up().build()
        }
        catch (e: RuntimeException) {
            return Health.down().build()
        }
    }

}
