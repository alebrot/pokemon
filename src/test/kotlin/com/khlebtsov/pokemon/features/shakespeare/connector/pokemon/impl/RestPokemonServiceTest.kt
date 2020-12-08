package com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.impl

import base.ServiceTest
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.dto.*
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.impl.properties.RestPokemonServiceProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.net.URI

@ContextConfiguration(classes = [RestPokemonService::class])
internal class RestPokemonServiceTest : ServiceTest() {

    @Autowired
    private lateinit var toTest: RestPokemonService

    @MockBean
    private lateinit var restTemplate: RestTemplate

    @MockBean
    private lateinit var properties: RestPokemonServiceProperties

    @Test
    fun getCharacteristicsOK() {
        //given
        val id = 1
        val highestStatName = "highestStatName"
        val baseUrl = "baseUrl"
        val userAgent = "userAgent"
        val pokemonCharacteristicsServiceRequest = PokemonCharacteristicsServiceRequest(id)
        val pokemonCharacteristicsServiceResponse = PokemonCharacteristicsServiceResponse(
            id, 1, listOf(1, 2, 3),
            HighestStatDto(highestStatName, "url"),
            arrayListOf(DescriptionDto("description", LanguageDto("en", "url")))
        )

        val headers = HttpHeaders()
        headers.set("User-Agent", userAgent)
        val requestEntity =
            RequestEntity<Void>(
                headers,
                HttpMethod.GET,
                URI.create("${baseUrl}/characteristic/${id}/")
            )

        //mock
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(properties.userAgent).thenReturn(userAgent)
        Mockito.`when`(restTemplate.exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java))
            .thenReturn(ResponseEntity(pokemonCharacteristicsServiceResponse, HttpStatus.OK))

        val response = toTest.getCharacteristics(pokemonCharacteristicsServiceRequest)

        //assert
        Assertions.assertNotNull(response)
        Assertions.assertEquals(pokemonCharacteristicsServiceResponse, response)
        Mockito.verify(properties).baseUrl
        Mockito.verify(properties).userAgent
        Mockito.verify(restTemplate).exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java)
    }

    @Test
    fun getCharacteristicsThrowsIllegalStateExceptionWhenStatusOkButBodyNull() {
        //given
        val id = 1
        val baseUrl = "baseUrl"
        val userAgent = "userAgent"
        val pokemonCharacteristicsServiceRequest = PokemonCharacteristicsServiceRequest(id)

        val headers = HttpHeaders()
        headers.set("User-Agent", userAgent)
        val requestEntity =
            RequestEntity<Void>(
                headers,
                HttpMethod.GET,
                URI.create("${baseUrl}/characteristic/${id}/")
            )

        //mock
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(properties.userAgent).thenReturn(userAgent)
        Mockito.`when`(restTemplate.exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java))
            .thenReturn(ResponseEntity(null, HttpStatus.OK))

        try {
            toTest.getCharacteristics(pokemonCharacteristicsServiceRequest)
            fail("should throw IllegalStateException")
        } catch (ignored: IllegalStateException) {
        }

        //assert
        Mockito.verify(properties).baseUrl
        Mockito.verify(properties).userAgent
        Mockito.verify(restTemplate).exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java)
    }

    @Test
    fun getCharacteristicsReturnsNullOn404() {
        //given
        val id = 1
        val baseUrl = "baseUrl"
        val userAgent = "userAgent"
        val pokemonCharacteristicsServiceRequest = PokemonCharacteristicsServiceRequest(id)

        val headers = HttpHeaders()
        headers.set("User-Agent", userAgent)
        val requestEntity =
            RequestEntity<Void>(
                headers,
                HttpMethod.GET,
                URI.create("${baseUrl}/characteristic/${id}/")
            )

        //mock
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(properties.userAgent).thenReturn(userAgent)
        Mockito.`when`(restTemplate.exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java))
            .thenThrow(
                HttpClientErrorException.create(
                    HttpStatus.NOT_FOUND,
                    "Not found",
                    HttpHeaders.EMPTY,
                    byteArrayOf(),
                    null
                )
            )
        val response = toTest.getCharacteristics(pokemonCharacteristicsServiceRequest)

        //assert
        Assertions.assertNull(response)
        Mockito.verify(properties).baseUrl
        Mockito.verify(properties).userAgent
        Mockito.verify(restTemplate).exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java)
    }

    @Test
    fun getCharacteristicsThrowsHttpClientErrorExceptionOnError() {
        //given
        val id = 1
        val baseUrl = "baseUrl"
        val userAgent = "userAgent"
        val pokemonCharacteristicsServiceRequest = PokemonCharacteristicsServiceRequest(id)

        val headers = HttpHeaders()
        headers.set("User-Agent", userAgent)
        val requestEntity =
            RequestEntity<Void>(
                headers,
                HttpMethod.GET,
                URI.create("${baseUrl}/characteristic/${id}/")
            )

        //mock
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(properties.userAgent).thenReturn(userAgent)
        Mockito.`when`(restTemplate.exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java))
            .thenThrow(
                HttpClientErrorException.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "some error",
                    HttpHeaders.EMPTY,
                    byteArrayOf(),
                    null
                )
            )
        try {
            toTest.getCharacteristics(pokemonCharacteristicsServiceRequest)
            fail("should throw HttpClientErrorException")
        } catch (exception: HttpClientErrorException) {
            //assert
            Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.statusCode)
        }

        Mockito.verify(properties).baseUrl
        Mockito.verify(properties).userAgent
        Mockito.verify(restTemplate).exchange(requestEntity, PokemonCharacteristicsServiceResponse::class.java)
    }

    @Test
    fun getPokemonOK() {
        //given
        val name = "pokemonName"
        val baseUrl = "baseUrl"
        val userAgent = "userAgent"
        val request = GetPokemonRequest(name)
        val getPokemonResponse = GetPokemonResponse(1, name, 2, 3)

        val headers = HttpHeaders()
        headers.set("User-Agent", userAgent)
        val requestEntity =
            RequestEntity<Void>(
                headers,
                HttpMethod.GET,
                URI.create("${baseUrl}/pokemon/${name}/")
            )
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(properties.userAgent).thenReturn(userAgent)
        Mockito.`when`(restTemplate.exchange(requestEntity, GetPokemonResponse::class.java))
            .thenReturn(ResponseEntity(getPokemonResponse, HttpStatus.OK))

        //run
        val pokemon = toTest.getPokemon(request)
        Assertions.assertNotNull(pokemon)
        Assertions.assertEquals(getPokemonResponse, pokemon)
        Mockito.verify(properties).baseUrl
        Mockito.verify(properties).userAgent
        Mockito.verify(restTemplate).exchange(requestEntity, GetPokemonResponse::class.java)
    }

    @Test
    internal fun getPokemonThrowsIllegalStateExceptionWhenStatusOkButBodyNull() {
        //given
        val name = "pokemonName"
        val baseUrl = "baseUrl"
        val userAgent = "userAgent"
        val request = GetPokemonRequest(name)

        val headers = HttpHeaders()
        headers.set("User-Agent", userAgent)
        val requestEntity =
            RequestEntity<Void>(
                headers,
                HttpMethod.GET,
                URI.create("${baseUrl}/pokemon/${name}/")
            )
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(properties.userAgent).thenReturn(userAgent)
        Mockito.`when`(restTemplate.exchange(requestEntity, GetPokemonResponse::class.java))
            .thenReturn(ResponseEntity(null, HttpStatus.OK))
        try {
            toTest.getPokemon(request)
            fail("should throw IllegalStateException")
        } catch (ignored: IllegalStateException) {
        }
        Mockito.verify(properties).baseUrl
        Mockito.verify(properties).userAgent
        Mockito.verify(restTemplate).exchange(requestEntity, GetPokemonResponse::class.java)
    }

    @Test
    fun getPokemonReturnsNullOn404() {
        //given
        val name = "pokemonName"
        val baseUrl = "baseUrl"
        val userAgent = "userAgent"
        val request = GetPokemonRequest(name)

        val headers = HttpHeaders()
        headers.set("User-Agent", userAgent)
        val requestEntity =
            RequestEntity<Void>(
                headers,
                HttpMethod.GET,
                URI.create("${baseUrl}/pokemon/${name}/")
            )
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(properties.userAgent).thenReturn(userAgent)
        Mockito.`when`(restTemplate.exchange(requestEntity, GetPokemonResponse::class.java)).thenThrow(
            HttpClientErrorException.create(
                HttpStatus.NOT_FOUND,
                "Not found",
                HttpHeaders.EMPTY,
                byteArrayOf(),
                null
            )
        )
        //run
        val pokemon = toTest.getPokemon(request)
        Assertions.assertNull(pokemon)
        Mockito.verify(properties).baseUrl
        Mockito.verify(properties).userAgent
        Mockito.verify(restTemplate).exchange(requestEntity, GetPokemonResponse::class.java)
    }

    @Test
    fun getPokemonThrowsHttpClientErrorExceptionOnError() {
        //given
        val name = "pokemonName"
        val baseUrl = "baseUrl"
        val userAgent = "userAgent"
        val request = GetPokemonRequest(name)

        val headers = HttpHeaders()
        headers.set("User-Agent", userAgent)
        val requestEntity =
            RequestEntity<Void>(
                headers,
                HttpMethod.GET,
                URI.create("${baseUrl}/pokemon/${name}/")
            )
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(properties.userAgent).thenReturn(userAgent)
        Mockito.`when`(restTemplate.exchange(requestEntity, GetPokemonResponse::class.java)).thenThrow(
            HttpClientErrorException.create(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "some error",
                HttpHeaders.EMPTY,
                byteArrayOf(),
                null
            )
        )

        try {
            toTest.getPokemon(request)
            fail("should throw HttpClientErrorException")
        } catch (exception: HttpClientErrorException) {
            //assert
            Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.statusCode)
        }

        Mockito.verify(properties).baseUrl
        Mockito.verify(properties).userAgent
        Mockito.verify(restTemplate).exchange(requestEntity, GetPokemonResponse::class.java)
    }
}
