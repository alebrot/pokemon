package com.khlebtsov.pokemon.features.shakespeare.connector.shakespeare.impl.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "connectors.rest.shakespeare-service")
class RestShakespeareServiceProperties(val baseUrl: String)
