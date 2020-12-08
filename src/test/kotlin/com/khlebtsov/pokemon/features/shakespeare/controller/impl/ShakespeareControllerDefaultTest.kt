package com.khlebtsov.pokemon.features.shakespeare.controller.impl

import base.ControllerTest
import com.khlebtsov.pokemon.errorhandler.ErrorResponse
import com.khlebtsov.pokemon.features.shakespeare.controller.dto.RetriveShakespeareDescriptionResponse
import com.khlebtsov.pokemon.features.shakespeare.model.Description
import com.khlebtsov.pokemon.features.shakespeare.model.Pokemon
import com.khlebtsov.pokemon.features.shakespeare.service.pokemon.RetrievePokemonService
import com.khlebtsov.pokemon.features.shakespeare.service.pokemon.dto.FindPokemonDescriptionRequest
import com.khlebtsov.pokemon.features.shakespeare.service.translator.TranslatorService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.net.URI

internal class ShakespeareControllerDefaultTest : ControllerTest() {

    @MockBean
    private lateinit var pokemonService: RetrievePokemonService

    @MockBean
    private lateinit var translatorService: TranslatorService

    @Test
    fun retriveShakespeareDescriptionOK() {
        //given
        val name = "pokemonName"
        val id = 1
        val pokemon = Pokemon(id, name)
        val languageIso = "en"
        val text = "text"
        val translatedText = "translatedText"
        val description = Description(text)

        //mock
        Mockito.`when`(pokemonService.findByName(name)).thenReturn(pokemon)
        Mockito.`when`(pokemonService.findPokemonDescription(FindPokemonDescriptionRequest(pokemon, languageIso)))
            .thenReturn(description)
        Mockito.`when`(translatorService.translate(text)).thenReturn(translatedText)
        //run
        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/pokemon/$name")))
            .andExpect(status().isOk)
            .andReturn()

        //assert
        val response = objectMapper.readValue(
            mvcResult.response.contentAsString,
            RetriveShakespeareDescriptionResponse::class.java
        )
        Assertions.assertNotNull(response)
        Assertions.assertEquals(name, response.name)
        Assertions.assertEquals(translatedText, response.description)

        Mockito.verify(pokemonService).findByName(name)
        Mockito.verify(pokemonService).findPokemonDescription(FindPokemonDescriptionRequest(pokemon, languageIso))
        Mockito.verify(translatorService).translate(text)
    }

    @Test
    fun retriveShakespeareAndFindByNameReturnsNull() {
        //given
        val name = "pokemonName"

        //mock
        Mockito.`when`(pokemonService.findByName(name)).thenReturn(null)
        //run
        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/pokemon/$name")))
            .andExpect(status().isNotFound)
            .andReturn()

        //assert
        val response = objectMapper.readValue(
            mvcResult.response.contentAsString,
            ErrorResponse::class.java
        )
        Assertions.assertNotNull(response)
        Assertions.assertEquals(
            "Something went wrong, please check log or enable displayErrorsToCaller",
            response.message
        )

        Mockito.verify(pokemonService).findByName(name)
        Mockito.verifyNoMoreInteractions(pokemonService)
        Mockito.verifyNoInteractions(translatorService)
    }


    @Test
    fun retriveShakespeareAndDescriptionReturnsNull() {
        //given
        val name = "pokemonName"
        val id = 1
        val pokemon = Pokemon(id, name)
        val languageIso = "en"
        val request = FindPokemonDescriptionRequest(pokemon, languageIso)

        //mock
        Mockito.`when`(pokemonService.findByName(name)).thenReturn(pokemon)
        Mockito.`when`(pokemonService.findPokemonDescription(request))
            .thenReturn(null)
        //run
        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/pokemon/$name")))
            .andExpect(status().isNotFound)
            .andReturn()

        //assert
        val response = objectMapper.readValue(
            mvcResult.response.contentAsString,
            ErrorResponse::class.java
        )
        Assertions.assertNotNull(response)
        Assertions.assertEquals(
            "Something went wrong, please check log or enable displayErrorsToCaller",
            response.message
        )

        Mockito.verify(pokemonService).findByName(name)
        Mockito.verify(pokemonService).findPokemonDescription(request)
        Mockito.verifyNoInteractions(translatorService)
    }
}
