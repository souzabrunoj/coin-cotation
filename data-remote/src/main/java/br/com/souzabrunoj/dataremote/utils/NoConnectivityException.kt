package br.com.souzabrunoj.dataremote.utils

import java.io.IOException

private const val NO_INTERNET_MESSAGE = "Check your internet connection and try again!"

class NoConnectivityException : IOException() {
    override val message: String
        get() = NO_INTERNET_MESSAGE
}