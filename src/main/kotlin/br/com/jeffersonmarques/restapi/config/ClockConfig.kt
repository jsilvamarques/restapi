package br.com.jeffersonmarques.restapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock
import java.time.ZoneId

@Configuration
class ClockConfig {

    @Bean
    fun clock(): Clock {
        return Clock.system(ZoneId.of("America/Sao_Paulo"))
    }
}