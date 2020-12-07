package com.khlebtsov.pokemon.features.shakespeare.service.pokemon.impl

import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.GetPokemonRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.PokemonCharacteristicsServiceRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.PokemonService
import com.khlebtsov.pokemon.features.shakespeare.model.Description
import com.khlebtsov.pokemon.features.shakespeare.model.Pokemon
import com.khlebtsov.pokemon.features.shakespeare.service.pokemon.FindPokemonDescriptionRequest
import com.khlebtsov.pokemon.features.shakespeare.service.pokemon.RetrievePokemonService
import org.springframework.stereotype.Service


@Service
class RetrievePokemonServiceImpl(private val pokemonService: PokemonService) : RetrievePokemonService {

    override fun findByName(name: String): Pokemon? {
        return pokemonService.getPokemon(GetPokemonRequest(name))?.let { Pokemon(it.id, it.name) }
    }

    override fun findPokemonDescription(request: FindPokemonDescriptionRequest): Description? {
        val languageIso = request.languageIso
        val id = request.pokemon.id
        return pokemonService.getCharacteristics(PokemonCharacteristicsServiceRequest(id))
            ?.let {
                it.descriptions.firstOrNull { descriptionDto -> languageIso == descriptionDto.language.name }
                    ?.let { descriptionDto -> Description(descriptionDto.language.name) }
            }
    }
}
