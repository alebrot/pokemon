package com.khlebtsov.pokemon.features.shakespeare.service.pokemon

import com.khlebtsov.pokemon.features.shakespeare.model.Description
import com.khlebtsov.pokemon.features.shakespeare.model.Pokemon

interface RetrievePokemonService {
    fun findByName(name: String): Pokemon?
    fun findPokemonDescription(request: FindPokemonDescriptionRequest): Description?
}
