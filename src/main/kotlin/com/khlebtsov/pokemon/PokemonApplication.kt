package com.khlebtsov.pokemon

import com.khlebtsov.pokemon.errorhandler.properties.ErrorHandleProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(ErrorHandleProperties::class)
@SpringBootApplication
class PokemonApplication

fun main(args: Array<String>) {
    runApplication<PokemonApplication>(*args)
}
