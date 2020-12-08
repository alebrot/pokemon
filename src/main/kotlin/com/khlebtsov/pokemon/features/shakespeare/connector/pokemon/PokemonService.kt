package com.khlebtsov.pokemon.features.shakespeare.connector.pokemon

import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.dto.GetPokemonRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.dto.GetPokemonResponse
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.dto.PokemonCharacteristicsServiceRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.dto.PokemonCharacteristicsServiceResponse

interface PokemonService {
    fun getCharacteristics(request: PokemonCharacteristicsServiceRequest): PokemonCharacteristicsServiceResponse?
    fun getPokemon(request: GetPokemonRequest): GetPokemonResponse?
}
