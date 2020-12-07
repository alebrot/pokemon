package com.khlebtsov.pokemon.errorhandler.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "error-handler")
data class ErrorHandleProperties(val displayErrorsToCaller: Boolean, val defaultErrorMessage: String)
