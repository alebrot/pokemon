package com.khlebtsov.pokemon.errorhandler

import com.khlebtsov.pokemon.errorhandler.factory.ErrorResponseFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ErrorHandler(private val errorResponseFactory: ErrorResponseFactory) {
    val log: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): ResponseEntity<Any> {
        log(ex, true)
        return ResponseEntity(errorResponseFactory.create(ex), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(HttpClientErrorException::class)
    fun handleHttpClientErrorException(ex: HttpClientErrorException): ResponseEntity<Any> {
        log(ex)
        return ResponseEntity(errorResponseFactory.create(ex), ex.statusCode)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Any> {
        log(ex)
        return ResponseEntity(errorResponseFactory.create(ex), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Any> {
        log(ex)
        return ResponseEntity(errorResponseFactory.create(ex), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun log(ex: Exception, suppressStacktrace: Boolean = false) {
        if (suppressStacktrace) {
            log.error(ex.message)
        } else {
            log.error(ex.message, ex)
        }
    }
}
