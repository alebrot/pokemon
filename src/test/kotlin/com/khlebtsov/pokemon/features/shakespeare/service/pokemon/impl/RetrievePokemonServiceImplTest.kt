package com.khlebtsov.pokemon.features.shakespeare.service.pokemon.impl

import base.ServiceTest
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.PokemonService
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.dto.*
import com.khlebtsov.pokemon.features.shakespeare.model.Pokemon
import com.khlebtsov.pokemon.features.shakespeare.service.pokemon.dto.FindPokemonDescriptionRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [RetrievePokemonServiceImpl::class])

internal class RetrievePokemonServiceImplTest : ServiceTest() {


    @Autowired
    private lateinit var toTest: RetrievePokemonServiceImpl

    @MockBean
    private lateinit var pokemonService: PokemonService

    @Test
    fun findByName() {
        //given
        val name = "name"
        val id = 1
        val getPokemonResponse = GetPokemonResponse(id, name, 2, 3)
        val getPokemonRequest = GetPokemonRequest(name)
        Mockito.`when`(pokemonService.getPokemon(getPokemonRequest)).thenReturn(getPokemonResponse)
        //run
        val result = toTest.findByName(name)
        //verify
        Assertions.assertNotNull(result)
        Assertions.assertEquals(id, result!!.id)
        Assertions.assertEquals(name, result.name)
        Mockito.verify(pokemonService).getPokemon(getPokemonRequest)
    }

    @Test
    fun findByNameNotFound() {
        //given
        val name = "name"
        val getPokemonRequest = GetPokemonRequest(name)
        Mockito.`when`(pokemonService.getPokemon(getPokemonRequest)).thenReturn(null)
        //run
        val result = toTest.findByName(name)
        //verify
        Assertions.assertNull(result)
        Mockito.verify(pokemonService).getPokemon(getPokemonRequest)
    }

    @Test
    fun findByNamePropagatesException() {
        // given
        val name = "name"
        val getPokemonRequest = GetPokemonRequest(name)
        Mockito.`when`(pokemonService.getPokemon(getPokemonRequest)).thenThrow(IllegalArgumentException("Simulated"))
        //run
        try {
            toTest.findByName(name)
            fail("expects IllegalArgumentException")
        } catch (ignored: IllegalArgumentException) {
        }
        //verify
        Mockito.verify(pokemonService).getPokemon(getPokemonRequest)
    }

    @Test
    fun findPokemonDescriptionOk() {
        //given
        val id = 1
        val name = "name"
        val languageIso = "en"

        val pokemonCharacteristicsServiceRequest = PokemonCharacteristicsServiceRequest(id)
        val description = "description"
        val pokemonCharacteristicsServiceResponse = PokemonCharacteristicsServiceResponse(
            id,
            1,
            arrayListOf(1, 2, 3),
            HighestStatDto("name", "url"),
            listOf(DescriptionDto(description, LanguageDto(languageIso, "url")))
        )
        Mockito.`when`(pokemonService.getCharacteristics(pokemonCharacteristicsServiceRequest))
            .thenReturn(pokemonCharacteristicsServiceResponse)

        //run
        val findPokemonDescription =
            toTest.findPokemonDescription(FindPokemonDescriptionRequest(Pokemon(id, name), languageIso))

        //verify
        Assertions.assertNotNull(findPokemonDescription)
        Assertions.assertEquals(description, findPokemonDescription!!.description)

        Mockito.verify(pokemonService).getCharacteristics(pokemonCharacteristicsServiceRequest)
    }

    @Test
    fun findPokemonDescriptionNotFoundEn() {
        //given
        val id = 1
        val name = "name"
        val languageIso = "en"

        val pokemonCharacteristicsServiceRequest = PokemonCharacteristicsServiceRequest(id)
        val description = "description"
        val pokemonCharacteristicsServiceResponse = PokemonCharacteristicsServiceResponse(
            id,
            1,
            arrayListOf(1, 2, 3),
            HighestStatDto("name", "url"),
            listOf(DescriptionDto(description, LanguageDto("it", "url")))
        )
        Mockito.`when`(pokemonService.getCharacteristics(pokemonCharacteristicsServiceRequest))
            .thenReturn(pokemonCharacteristicsServiceResponse)

        //run
        val findPokemonDescription =
            toTest.findPokemonDescription(FindPokemonDescriptionRequest(Pokemon(id, name), languageIso))

        //verify
        Assertions.assertNull(findPokemonDescription)

        Mockito.verify(pokemonService).getCharacteristics(pokemonCharacteristicsServiceRequest)
    }

    @Test
    fun findPokemonDescriptionNotFound() {
        //given
        val id = 1
        val name = "name"
        val languageIso = "en"

        val pokemonCharacteristicsServiceRequest = PokemonCharacteristicsServiceRequest(id)

        Mockito.`when`(pokemonService.getCharacteristics(pokemonCharacteristicsServiceRequest))
            .thenReturn(null)

        //run
        val findPokemonDescription =
            toTest.findPokemonDescription(FindPokemonDescriptionRequest(Pokemon(id, name), languageIso))

        //verify
        Assertions.assertNull(findPokemonDescription)

        Mockito.verify(pokemonService).getCharacteristics(pokemonCharacteristicsServiceRequest)
    }

    @Test
    fun findPokemonDescriptionPropagatesException() {
        //given
        val id = 1
        val name = "name"
        val languageIso = "en"

        val pokemonCharacteristicsServiceRequest = PokemonCharacteristicsServiceRequest(id)

        Mockito.`when`(pokemonService.getCharacteristics(pokemonCharacteristicsServiceRequest))
            .thenThrow(IllegalArgumentException("Simulated"))

        //run
        try {

            toTest.findPokemonDescription(FindPokemonDescriptionRequest(Pokemon(id, name), languageIso))
            fail("Expects IllegalArgumentException")
        } catch (ignored: IllegalArgumentException) {
        }

        //verify
        Mockito.verify(pokemonService).getCharacteristics(pokemonCharacteristicsServiceRequest)
    }
}
