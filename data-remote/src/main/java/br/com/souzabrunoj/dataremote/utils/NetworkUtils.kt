package br.com.souzabrunoj.dataremote.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.http.HTTP_INTERNAL_SERVER_ERROR
import retrofit2.Response

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Result<T> = try {
    val result = withContext(Dispatchers.IO) { call() }
    withContext(Dispatchers.Main) {
        val data = result.body()
        if (data != null) {
            Result.success(data)
        } else {
            HTTP_INTERNAL_SERVER_ERROR
            Result.failure(result.toError())
        }
    }
} catch (t: Throwable) {
    withContext(Dispatchers.Main) { Result.failure(t) }
}

fun <T> Response<T>.toError(): Error {
    val errorMessage = when (this.code()) {
        in 400..499 -> CLIENT_MESSAGE_ERROR
        in 500..599 -> SERVER_MESSAGE_ERROR
        else -> UNKNOWN_MESSAGE_ERROR
    }
    return Error(errorMessage)
}
