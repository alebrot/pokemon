package com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare

import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.dto.TranslateRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.dto.TranslateResponse

interface ShakespeareService {
    fun translate(request: TranslateRequest): TranslateResponse
}
