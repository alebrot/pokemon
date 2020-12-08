package com.khlebtsov.pokemon.features.shakespeare.service.translator.impl

import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.ShakespeareService
import com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.dto.TranslateRequest
import com.khlebtsov.pokemon.features.shakespeare.service.translator.TranslatorService
import org.springframework.stereotype.Service

@Service
class TranslatorServiceImpl(private val shakespeareService: ShakespeareService) : TranslatorService {
    override fun translate(text: String): String {
        return shakespeareService.translate(TranslateRequest(text)).contents.translated
    }
}
