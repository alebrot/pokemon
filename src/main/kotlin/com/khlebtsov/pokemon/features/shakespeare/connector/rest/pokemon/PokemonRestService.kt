package com.khlebtsov.pokemon.features.shakespeare.connector.rest.pokemon

import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class PokemonRestService(private val restTemplate: RestTemplate) {

    val base: String = "https://pokeapi.co/api/v2"

    fun getCharacteristics(request: PokemonCharacteristicsServiceRequest): PokemonCharacteristicsServiceResponse? {
        return try {
            restTemplate.getForEntity(
                "$base/${request.id}/", PokemonCharacteristicsServiceResponse::class.java
            ).body ?: throw IllegalStateException("Unexpected result, body is null")
        } catch (notFoundException: HttpClientErrorException.NotFound) {
            null
        }
    }

    fun getPokemon(request: GetPokemonRequest): GetPokemonResponse? {
        return try {
            restTemplate.getForEntity(
                "$base/pokemon/${request.name}/", GetPokemonResponse::class.java
            ).body ?: throw IllegalStateException("Unexpected result, body is null")
        } catch (notFoundException: HttpClientErrorException.NotFound) {
            null
        }
    }
}
