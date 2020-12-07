package com.khlebtsov.pokemon.features.shakespeare.service.rest.pokemon

import com.fasterxml.jackson.annotation.JsonProperty


//getCharacteristics
data class HighestStatDto(val name: String, val url: String)
data class LanguageDto(val name: String, val url: String)
data class DescriptionDto(val description: String, val language: LanguageDto)

data class PokemonCharacteristicsServiceRequest(val id: Int)
data class PokemonCharacteristicsServiceResponse(
    val id: String,
    @JsonProperty("gene_modulo") val geneModulo: Int,
    @JsonProperty("possible_values") val possibleValues: List<Int>,
    @JsonProperty("highest_stat") val highestStat: HighestStatDto,
    val descriptions: List<DescriptionDto>
)

//getPokemon
data class GetPokemonRequest(val name: String)
data class GetPokemonResponse(val id: Int, val name: String, val height: Int, val weight: Int)

