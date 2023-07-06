package br.com.souzabrunoj.dataremote.service

import br.com.souzabrunoj.dataremote.response.CoinDetailsResponse
import br.com.souzabrunoj.dataremote.response.CoinItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("coins/markets?sparkline=true")
    suspend fun getCoinQuotation(@Query("vs_currency") coin: String): Response<List<CoinItemResponse>>

    @GET("coins/{id}?sparkline=true")
    suspend fun getCoinDetails(@Path("id") id: String): Response<CoinDetailsResponse>
}