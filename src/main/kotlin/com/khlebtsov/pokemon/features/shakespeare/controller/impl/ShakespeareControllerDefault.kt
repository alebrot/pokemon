package com.khlebtsov.pokemon.features.shakespeare.controller.impl

import com.khlebtsov.pokemon.features.shakespeare.controller.ShakespeareController
import com.khlebtsov.pokemon.features.shakespeare.controller.dto.RetriveShakespeareDescriptionResponse
import com.khlebtsov.pokemon.features.shakespeare.model.Pokemon
import com.khlebtsov.pokemon.features.shakespeare.service.pokemon.RetrievePokemonService
import com.khlebtsov.pokemon.features.shakespeare.service.pokemon.dto.FindPokemonDescriptionRequest
import com.khlebtsov.pokemon.features.shakespeare.service.translator.TranslatorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ShakespeareControllerDefault(
    private val pokemonService: RetrievePokemonService,
    private val translatorService: TranslatorService
) : ShakespeareController {
    @GetMapping("/pokemon/{name}")
    override fun retriveShakespeareDescription(@PathVariable name: String): RetriveShakespeareDescriptionResponse {
        val pokemon = pokemonService.findByName(name)?.let {
            Pokemon(it.id, it.name)
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val pokemonDescription =
            pokemonService.findPokemonDescription(FindPokemonDescriptionRequest(pokemon = pokemon, "en"))
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        val translation = translatorService.translate(pokemonDescription.description)
        return RetriveShakespeareDescriptionResponse(name, translation)
    }
}
