package com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl

import com.khlebtsov.pokemon.features.shakespeare.connector.rest.shakespeare.TranslateRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.rest.shakespeare.TranslateResponse
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.ShakespeareService
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class ShakespeareServiceRest(private val restTemplate: RestTemplate) : ShakespeareService {
    private val base: String = "https://api.funtranslations.com"
    override fun translate(request: TranslateRequest): TranslateResponse {
        return restTemplate.postForEntity(
            "$base/translate/shakespeare.json",
            request,
            TranslateResponse::class.java
        ).body ?: throw IllegalStateException("Unexpected result, body is null")
    }
}
