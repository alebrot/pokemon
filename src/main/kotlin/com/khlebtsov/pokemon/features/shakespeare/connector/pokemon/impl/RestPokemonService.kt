package com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.impl

import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.*
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.impl.properties.RestPokemonServiceProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class RestPokemonService(
    private val restTemplate: RestTemplate,
    private val properties: RestPokemonServiceProperties
) : PokemonService {
    override fun getCharacteristics(request: PokemonCharacteristicsServiceRequest): PokemonCharacteristicsServiceResponse? {
        return try {
            val requestEntity =
                RequestEntity<Void>(
                    getRequiredHeaders(),
                    HttpMethod.GET,
                    URI.create("${properties.baseUrl}/characteristic/${request.id}/")
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
                RequestEntity<Void>(
                    getRequiredHeaders(),
                    HttpMethod.GET,
                    URI.create("${properties.baseUrl}/pokemon/${request.name}/")
                )
            restTemplate.exchange(requestEntity, GetPokemonResponse::class.java).body
                ?: throw IllegalStateException("Unexpected result, body is null")
        } catch (notFoundException: HttpClientErrorException.NotFound) {
            null
        }
    }

    private fun getRequiredHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.set("User-Agent", properties.userAgent)
        return headers
    }
}
