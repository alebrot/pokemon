package com.khlebtsov.pokemon.features.shakespeare.service.pokemon.dto

import com.khlebtsov.pokemon.features.shakespeare.model.Pokemon

data class FindPokemonDescriptionRequest(val pokemon: Pokemon, val languageIso: String)

