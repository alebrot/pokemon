package com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl

import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.ShakespeareService
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.dto.TranslateRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.dto.TranslateResponse
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.properties.RestShakespeareServiceProperties
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RestShakespeareService(
    private val restTemplate: RestTemplate,
    private val properties: RestShakespeareServiceProperties
) : ShakespeareService {
    override fun translate(request: TranslateRequest): TranslateResponse {
        return restTemplate.postForEntity(
            "${properties.baseUrl}/translate/shakespeare.json",
            request,
            TranslateResponse::class.java
        ).body ?: throw IllegalStateException("Unexpected result, body is null")
    }
}
