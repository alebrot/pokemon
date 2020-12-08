package com.khlebtsov.pokemon.errorhandler.factory

import base.ServiceTest
import com.khlebtsov.pokemon.errorhandler.properties.ErrorHandleProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [ErrorResponseFactory::class])

internal class ErrorResponseFactoryTest : ServiceTest() {


    @Autowired
    private lateinit var toTest: ErrorResponseFactory

    @MockBean
    private lateinit var properties: ErrorHandleProperties

    @Test
    fun createWhenDisplayErrorsToCallerTrue() {
        //given
        val exceptionOriginalMessage = "Simulated"
        val exception = IllegalArgumentException(exceptionOriginalMessage)

        Mockito.`when`(properties.displayErrorsToCaller).thenReturn(true)

        //run
        val errorResponse = toTest.create(exception)
        //assert
        Assertions.assertEquals(exceptionOriginalMessage, errorResponse.message)
        Mockito.verify(properties).displayErrorsToCaller
        Mockito.verifyNoMoreInteractions(properties)
    }

    @Test
    fun createWhenDisplayErrorsToCallerTrueAndMessageIsNull() {

        //given
        val exception = Exception()

        Mockito.`when`(properties.displayErrorsToCaller).thenReturn(true)
        val defaultMessage = "DefaultMessage"
        Mockito.`when`(properties.defaultErrorMessage).thenReturn(defaultMessage)

        //run
        val errorResponse = toTest.create(exception)
        //assert
        Assertions.assertEquals(defaultMessage, errorResponse.message)
        Mockito.verify(properties).displayErrorsToCaller
        Mockito.verify(properties).defaultErrorMessage
    }


    @Test
    fun createWhenDisplayErrorsToCallerFalse() {
        //given
        val exception = IllegalArgumentException("Simulated")
        val defaultMessage = "DefaultMessage"

        Mockito.`when`(properties.displayErrorsToCaller).thenReturn(false)
        Mockito.`when`(properties.defaultErrorMessage).thenReturn(defaultMessage)
        //run
        val errorResponse = toTest.create(exception)
        //assert
        Assertions.assertEquals(defaultMessage, errorResponse.message)
        Mockito.verify(properties).displayErrorsToCaller
        Mockito.verify(properties).defaultErrorMessage

    }
}
