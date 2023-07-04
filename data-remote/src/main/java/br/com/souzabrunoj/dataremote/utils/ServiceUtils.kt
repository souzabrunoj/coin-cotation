package br.com.souzabrunoj.dataremote.utils

suspend fun <T> apiCall(call: suspend () -> T) = try {
    Result.success(call())
} catch (t: Throwable) {
    Result.failure(t)
}