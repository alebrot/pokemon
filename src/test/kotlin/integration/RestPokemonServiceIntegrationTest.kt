package integration

import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.GetPokemonRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.PokemonCharacteristicsServiceRequest
import com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.impl.RestPokemonService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

@Disabled
@ExtendWith(value = [SpringExtension::class])
@ContextConfiguration(classes = [RestPokemonService::class, RestTemplate::class])
internal class RestPokemonServiceIntegrationTest(@Autowired private val toTest: RestPokemonService) {

    @Test
    fun getCharacteristics() {
        val characteristics = toTest.getCharacteristics(PokemonCharacteristicsServiceRequest(12))
        assertNotNull(characteristics)
        assertEquals(3, characteristics!!.descriptions.size)
        val englishDescription = characteristics.descriptions.firstOrNull { it.language.name == "en" }
        assertNotNull(englishDescription)
        assertNotNull("Alert to sounds", englishDescription!!.description)
    }

    @Test
    fun getPokemon() {
        val name = "butterfree"
        val response = toTest.getPokemon(GetPokemonRequest(name))
        assertNotNull(response)
        assertEquals(name, response!!.name)
        assertEquals(12, response.id)
        assertEquals(11, response.height)
        assertEquals(320, response.weight)
    }
}
