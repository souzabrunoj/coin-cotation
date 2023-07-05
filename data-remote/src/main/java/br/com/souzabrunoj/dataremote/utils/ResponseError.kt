package br.com.souzabrunoj.dataremote.utils

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("message") val message: String?,
    @SerializedName("status") val errorCode: Int?,
    @SerializedName("error") val error: String?
) {
    fun getErrorMessage(): String = message ?: error ?: ""
}