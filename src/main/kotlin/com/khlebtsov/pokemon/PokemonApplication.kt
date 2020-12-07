package com.khlebtsov.pokemon

import com.khlebtsov.pokemon.errorhandler.properties.ErrorHandleProperties
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.impl.properties.RestPokemonServiceProperties
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.properties.RestShakespeareServiceProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(
    ErrorHandleProperties::class,
    RestPokemonServiceProperties::class,
    RestShakespeareServiceProperties::class
)
@SpringBootApplication
class PokemonApplication

fun main(args: Array<String>) {
    runApplication<PokemonApplication>(*args)
}
