package com.khlebtsov.pokemon.features.shakespeare.connector.rest.shakespeare

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ShakespeareService(private val restTemplate: RestTemplate) {
    private val base: String = "https://api.funtranslations.com"

    fun translate(request: TranslateRequest): TranslateResponse {
        return restTemplate.postForEntity(
            "$base/translate/shakespeare.json",
            request,
            TranslateResponse::class.java
        ).body ?: throw IllegalStateException("Unexpected result, body is null")
    }
}
