package br.com.souzabrunoj.dataremote.utils

sealed class ErrorHolder(override val message: String) : Throwable(message) {
    data class BadRequest(override val message: String) : ErrorHolder(message)
    data class UnAuthorized(override val message: String) : ErrorHolder(message)
    data class InternalServerError(override val message: String) : ErrorHolder(message)
    data class ResourceNotFound(override val message: String) : ErrorHolder(message)
    data class Unknown(override val message: String) : ErrorHolder(message)
}