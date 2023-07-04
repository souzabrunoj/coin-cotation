package br.com.souzabrunoj.dataremote.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(call: suspend () -> T): Result<T> = try {
    val result = withContext(Dispatchers.IO) { call() }
    withContext(Dispatchers.Main) { Result.success(result) }
} catch (t: Throwable) {
    withContext(Dispatchers.Main) { Result.failure(t) }
}

