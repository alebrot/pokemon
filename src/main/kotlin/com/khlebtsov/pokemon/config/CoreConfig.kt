package com.khlebtsov.pokemon.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class CoreConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
