package com.khlebtsov.pokemon.features.shakespeare.service.translator.impl

import base.ServiceTest
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TranslatorServiceImpl::class])
internal class TranslatorServiceImplTest : ServiceTest() {

    @Autowired
    private lateinit var toTest: TranslatorServiceImpl

    @MockBean
    private lateinit var shakespeareService: ShakespeareService

    @Test
    fun translateOk() {
        //given
        val text = "text"
        val translated = "translated text"

        val translateRequest = TranslateRequest(text)
        val translateResponse = TranslateResponse(
            SuccessDto(1),
            ContentsDto(translated, text, "translation")
        )
        Mockito.`when`(shakespeareService.translate(translateRequest)).thenReturn(
            translateResponse
        )
        //run
        val result = toTest.translate(text)

        //assert
        Assertions.assertEquals(translated, result)
        Mockito.verify(shakespeareService).translate(translateRequest)
    }

    @Test
    fun translatePropagatesException() {
        //given
        val text = "text"
        val translateRequest = TranslateRequest(text)

        Mockito.`when`(shakespeareService.translate(translateRequest)).thenThrow(IllegalArgumentException("Simulated"))

        //run
        try {
            toTest.translate(text)
            fail("Expects IllegalArgumentException")
        } catch (ignored: IllegalArgumentException) {
        }

        //assert
        Mockito.verify(shakespeareService).translate(translateRequest)
    }
}
