package com.khlebtsov.pokemon.errorhandler.factory

import com.khlebtsov.pokemon.errorhandler.ErrorResponse
import com.khlebtsov.pokemon.errorhandler.properties.ErrorHandleProperties
import org.springframework.stereotype.Service

@Service
class ErrorResponseFactory(private val errorHandleProperties: ErrorHandleProperties) {

    fun create(exception: Exception): ErrorResponse {
        return if (errorHandleProperties.displayErrorsToCaller) {
            ErrorResponse(exception.message ?: errorHandleProperties.defaultErrorMessage)
        } else {
            ErrorResponse(errorHandleProperties.defaultErrorMessage)
        }
    }
}
