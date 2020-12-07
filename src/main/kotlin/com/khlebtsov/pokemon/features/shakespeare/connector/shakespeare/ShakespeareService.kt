package com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare

import com.khlebtsov.pokemon.features.shakespeare.connector.rest.shakespeare.TranslateRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.rest.shakespeare.TranslateResponse

interface ShakespeareService {
    fun translate(request: TranslateRequest): TranslateResponse
}
