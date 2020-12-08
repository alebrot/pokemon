package com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl

import base.ServiceTest
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.ContentsDto
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.SuccessDto
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.TranslateRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.TranslateResponse
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.properties.RestShakespeareServiceProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@ContextConfiguration(classes = [RestShakespeareService::class])
internal class RestShakespeareServiceTest : ServiceTest() {

    @Autowired
    private lateinit var toTest: RestShakespeareService

    @MockBean
    private lateinit var restTemplate: RestTemplate

    @MockBean
    private lateinit var properties: RestShakespeareServiceProperties

    @Test
    fun translateOk() {
        //given
        val baseUrl = "baseUrl"
        val text = "text"
        val translateRequest = TranslateRequest(text)
        val translateResponse = TranslateResponse(SuccessDto(1), ContentsDto("translated text", text, "translation"))
        val url = "$baseUrl/translate/shakespeare.json"

        //mock
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(
            restTemplate.postForEntity(
                url,
                translateRequest, TranslateResponse::class.java
            )
        )
            .thenReturn(ResponseEntity(translateResponse, HttpStatus.OK))

        //run
        val response = toTest.translate(translateRequest)
        Assertions.assertNotNull(response)
        Assertions.assertEquals(translateResponse, response)
        Mockito.verify(properties).baseUrl
        Mockito.verify(restTemplate).postForEntity(url, translateRequest, TranslateResponse::class.java)
    }

    @Test
    fun translateThrowsIllegalStateExceptionWhenStatusOkButBodyNull() {

        //given
        val baseUrl = "baseUrl"
        val text = "text"
        val translateRequest = TranslateRequest(text)
        val url = "$baseUrl/translate/shakespeare.json"

        //mock
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(
            restTemplate.postForEntity(
                url,
                translateRequest, TranslateResponse::class.java
            )
        )
            .thenReturn(ResponseEntity(null, HttpStatus.OK))

        //run

        try {
            toTest.translate(translateRequest)
            fail("should throw IllegalStateException")
        } catch (ignored: IllegalStateException) {
        }
        Mockito.verify(properties).baseUrl
        Mockito.verify(restTemplate).postForEntity(url, translateRequest, TranslateResponse::class.java)
    }


    @Test
    fun translateThrowsHttpClientErrorExceptionOnError() {
        //given
        val baseUrl = "baseUrl"
        val text = "text"
        val translateRequest = TranslateRequest(text)
        val url = "$baseUrl/translate/shakespeare.json"

        //mock
        Mockito.`when`(properties.baseUrl).thenReturn(baseUrl)
        Mockito.`when`(
            restTemplate.postForEntity(
                url,
                translateRequest, TranslateResponse::class.java
            )
        ).thenThrow(
            HttpClientErrorException.create(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "some error",
                HttpHeaders.EMPTY,
                byteArrayOf(),
                null
            )
        )

        //run
        try {
            toTest.translate(translateRequest)
            fail("should throw HttpClientErrorException")
        } catch (exception: HttpClientErrorException) {
            //assert
            Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.statusCode)
        }
        //assert
        Mockito.verify(properties).baseUrl
        Mockito.verify(restTemplate).postForEntity(url, translateRequest, TranslateResponse::class.java)
    }
}
