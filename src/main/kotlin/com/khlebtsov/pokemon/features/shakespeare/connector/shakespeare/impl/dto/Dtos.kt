package com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.dto


data class SuccessDto(val total: Int)
data class ContentsDto(val translated: String, val text: String, val translation: String)

data class TranslateRequest(val text: String)
data class TranslateResponse(val success: SuccessDto, val contents: ContentsDto)
