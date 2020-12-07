package com.khlebtsov.pokemon.features.shakespeare.service.internal.pokemon

import com.khlebtsov.pokemon.features.shakespeare.model.Pokemon

data class FindPokemonDescriptionRequest(val pokemon: Pokemon, val languageIso: String)

