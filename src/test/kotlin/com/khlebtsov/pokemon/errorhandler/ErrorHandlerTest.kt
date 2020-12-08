package com.khlebtsov.pokemon.errorhandler

import base.ServiceTest
import com.khlebtsov.pokemon.errorhandler.factory.ErrorResponseFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException

@ContextConfiguration(classes = [ErrorHandler::class])
internal class ErrorHandlerTest : ServiceTest() {
    @Autowired
    private lateinit var toTest: ErrorHandler

    @MockBean
    private lateinit var errorResponseFactory: ErrorResponseFactory

    @Test
    fun handleResponseStatusException() {
        val exception = ResponseStatusException(HttpStatus.NOT_FOUND)
        val errorResponse = ErrorResponse("Error")
        Mockito.`when`(errorResponseFactory.create(exception)).thenReturn(errorResponse)

        val responseEntity =
            toTest.handleResponseStatusException(exception)
        Assertions.assertNotNull(responseEntity)
        Assertions.assertEquals(errorResponse, responseEntity.body)
        Assertions.assertEquals(exception.status, responseEntity.statusCode)

        Mockito.verify(errorResponseFactory).create(exception)
    }

    @Test
    fun handleHttpClientErrorException() {
        val exception = HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)
        val errorResponse = ErrorResponse("Error")
        Mockito.`when`(errorResponseFactory.create(exception)).thenReturn(errorResponse)

        val responseEntity =
            toTest.handleHttpClientErrorException(exception)
        Assertions.assertNotNull(responseEntity)
        Assertions.assertEquals(errorResponse, responseEntity.body)
        Assertions.assertEquals(exception.rawStatusCode, responseEntity.statusCode.value())

        Mockito.verify(errorResponseFactory).create(exception)
    }

    @Test
    fun handleIllegalArgumentException() {
        val exception = IllegalArgumentException()
        val errorResponse = ErrorResponse("Error")
        Mockito.`when`(errorResponseFactory.create(exception)).thenReturn(errorResponse)

        val responseEntity =
            toTest.handleIllegalArgumentException(exception)
        Assertions.assertNotNull(responseEntity)
        Assertions.assertEquals(errorResponse, responseEntity.body)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)

        Mockito.verify(errorResponseFactory).create(exception)
    }

    @Test
    fun handleException() {
        val message = "Error"
        val exception = IllegalStateException(message)
        val errorResponse = ErrorResponse(message)
        Mockito.`when`(errorResponseFactory.create(exception)).thenReturn(errorResponse)

        val responseEntity =
            toTest.handleException(exception)
        Assertions.assertNotNull(responseEntity)
        Assertions.assertEquals(errorResponse, responseEntity.body)
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.statusCode)

        Mockito.verify(errorResponseFactory).create(exception)
    }
}
