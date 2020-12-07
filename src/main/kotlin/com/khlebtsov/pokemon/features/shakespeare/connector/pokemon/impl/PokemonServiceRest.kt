package com.khlebtsov.pokemon.features.shakespeare.connector.pokemon

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.net.URI


@Service
class PokemonServiceDefault(private val restTemplate: RestTemplate) : PokemonService {

    private val base: String = "https://pokeapi.co/api/v2"

    override fun getCharacteristics(request: PokemonCharacteristicsServiceRequest): PokemonCharacteristicsServiceResponse? {
        return try {
            val requestEntity =
                RequestEntity<Void>(
                    getRequiredHeaders(),
                    HttpMethod.GET,
                    URI.create("$base/characteristic/${request.id}/")
                )
            restTemplate.exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java).body
                ?: throw IllegalStateException("Unexpected result, body is null")
        } catch (notFoundException: HttpClientErrorException.NotFound) {
            null
        }
    }

    override fun getPokemon(request: GetPokemonRequest): GetPokemonResponse? {
        return try {
            val requestEntity =
                RequestEntity<Void>(getRequiredHeaders(), HttpMethod.GET, URI.create("$base/pokemon/${request.name}/"))
            restTemplate.exchange(requestEntity, GetPokemonResponse::class.java).body
                ?: throw IllegalStateException("Unexpected result, body is null")
        } catch (notFoundException: HttpClientErrorException.NotFound) {
            null
        }
    }


    private fun getRequiredHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.set("User-Agent", "Mozilla/5.0")
        return headers
    }
}
