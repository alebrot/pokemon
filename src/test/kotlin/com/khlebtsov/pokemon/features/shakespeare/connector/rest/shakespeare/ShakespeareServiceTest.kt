package com.khlebtsov.pokemon.features.shakespeare.connector.rest.shakespeare

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

@ExtendWith(value = [SpringExtension::class])
@ContextConfiguration(classes = [ShakespeareService::class, RestTemplate::class])
internal class ShakespeareServiceTest(@Autowired private val toTest: ShakespeareService) {
    @Test
    fun translate() {
        val text = "Hello world"
        val translateResponse = toTest.translate(TranslateRequest(text))
        Assertions.assertNotNull(text, translateResponse.contents.text)
        Assertions.assertEquals("shakespeare", translateResponse.contents.translation)
        Assertions.assertEquals("Valorous morrow to thee,  sir ordinary", translateResponse.contents.translated)
        Assertions.assertEquals(1, translateResponse.success.total)
    }
}
