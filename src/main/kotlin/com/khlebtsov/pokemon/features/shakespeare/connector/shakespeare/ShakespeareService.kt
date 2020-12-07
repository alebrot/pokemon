package com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare

interface ShakespeareService {
    fun translate(request: TranslateRequest): TranslateResponse
}
