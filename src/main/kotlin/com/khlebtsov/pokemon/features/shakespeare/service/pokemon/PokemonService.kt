package com.khlebtsov.pokemon.features.shakespeare.service.pokemon

import com.khlebtsov.pokemon.features.shakespeare.connector.rest.pokemon.GetPokemonRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.rest.pokemon.PokemonCharacteristicsServiceRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.rest.pokemon.PokemonRestService
import com.khlebtsov.pokemon.features.shakespeare.model.Description
import com.khlebtsov.pokemon.features.shakespeare.model.Pokemon
import org.springframework.stereotype.Service


@Service
class PokemonService(private val pokemonRestService: PokemonRestService) {

    fun findByName(name: String): Pokemon? {
        return pokemonRestService.getPokemon(GetPokemonRequest(name))?.let { Pokemon(it.id, it.name) }
    }

    fun findPokemonDescription(request: FindPokemonDescriptionRequest): Description? {
        val languageIso = request.languageIso
        val id = request.pokemon.id
        return pokemonRestService.getCharacteristics(PokemonCharacteristicsServiceRequest(id))
            ?.let {
                it.descriptions.firstOrNull { descriptionDto -> languageIso == descriptionDto.language.name }
                    ?.let { descriptionDto -> Description(descriptionDto.language.name) }
            }
    }
}
