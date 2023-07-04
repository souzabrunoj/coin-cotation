package br.com.souzabrunoj.dataremote.utils

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = NO_INTERNET_MESSAGE
}