package br.com.souzabrunoj.dataremote.service

import br.com.souzabrunoj.dataremote.response.CoinItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("coins/markets?sparkline=true")
    suspend fun getCoinQuotation(@Query("vs_currency") coin: String): List<CoinItemResponse>
}