package com.khlebtsov.pokemon.features.shakespeare.service.translator

import com.khlebtsov.pokemon.features.shakespeare.connector.rest.shakespeare.ShakespeareService
import com.khlebtsov.pokemon.features.shakespeare.connector.rest.shakespeare.TranslateRequest
import org.springframework.stereotype.Service

@Service
class TranslatorService(private val shakespeareService: ShakespeareService) {
    fun translate(text: String): String {
        return shakespeareService.translate(TranslateRequest(text)).contents.translated
    }
}
