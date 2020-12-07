package com.khlebtsov.pokemon.features.shakespeare.connector.pokemon

interface PokemonService {
    fun getCharacteristics(request: PokemonCharacteristicsServiceRequest): PokemonCharacteristicsServiceResponse?
    fun getPokemon(request: GetPokemonRequest): GetPokemonResponse?
}
