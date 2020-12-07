package com.khlebtsov.pokemon.features.shakespeare.connector.pokemon.impl.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "connectors.rest.pokemon-service")
data class RestPokemonServiceProperties(val baseUrl: String, val userAgent: String)
