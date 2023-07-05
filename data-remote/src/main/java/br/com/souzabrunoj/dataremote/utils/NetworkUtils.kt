package br.com.souzabrunoj.dataremote.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okhttp3.internal.http.HTTP_BAD_REQUEST
import okhttp3.internal.http.HTTP_INTERNAL_SERVER_ERROR
import okhttp3.internal.http.HTTP_NOT_FOUND
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import retrofit2.HttpException
import retrofit2.Response
import java.io.InputStreamReader

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Result<T> = try {
    val result = withContext(Dispatchers.IO) { call() }
    withContext(Dispatchers.Main) {
        val data = result.body()
        if (data != null) {
            Result.success(data)
        } else {
            Result.failure(result.toError())
        }
    }
} catch (t: Throwable) {
    withContext(Dispatchers.Main) {
        if (t is HttpException) {
            Result.failure(getHttpErrorMessage(t.response()?.errorBody()))
        } else {
            Result.failure(t)
        }
    }
}

fun <T> Response<T>.toError(): Error {
    val errorMessage = when (this.code()) {
        in 400..499 -> CLIENT_MESSAGE_ERROR
        in 500..599 -> SERVER_MESSAGE_ERROR
        else -> UNKNOWN_MESSAGE_ERROR
    }
    return Error(errorMessage)
}

private fun getHttpErrorMessage(response: ResponseBody?): ErrorHolder {
    val inputStream = InputStreamReader(response.toString().byteInputStream(), Charsets.UTF_8)
    val errorResponse = Gson().fromJson<ResponseError>(inputStream, object : TypeToken<ResponseError>() {}.type)
    val message = errorResponse.getErrorMessage()
    return when (errorResponse.errorCode) {
        HTTP_INTERNAL_SERVER_ERROR -> ErrorHolder.InternalServerError(message)
        HTTP_BAD_REQUEST -> ErrorHolder.BadRequest(message)
        HTTP_UNAUTHORIZED -> ErrorHolder.UnAuthorized(message)
        HTTP_NOT_FOUND -> ErrorHolder.ResourceNotFound(message)
        else -> ErrorHolder.Unknown(message)
    }
}



