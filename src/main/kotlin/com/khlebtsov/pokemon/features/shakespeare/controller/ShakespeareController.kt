package com.khlebtsov.pokemon.features.shakespeare.controller

import com.khlebtsov.pokemon.features.shakespeare.controller.dto.RetriveShakespeareDescriptionResponse

interface ShakespeareController {
    fun retriveShakespeareDescription(name: String): RetriveShakespeareDescriptionResponse
}
