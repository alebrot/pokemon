package com.khlebtsov.pokemon.features.shakespeare.service.rest.characteristics

import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class PokemonCharacteristicsService(private val restTemplate: RestTemplate) {
    fun getCharacteristics(request: PokemonCharacteristicsServiceRequest): PokemonCharacteristicsServiceResponse? {
        val s = "https://pokeapi.co/api/v2/characteristic/${request.id}/"
        return try {
            restTemplate.getForEntity(
                s, PokemonCharacteristicsServiceResponse::class.java
            ).body ?: throw IllegalStateException("Unexpected result, body is null")
        } catch (notFoundException: HttpClientErrorException.NotFound) {
            null
        }
    }
}
