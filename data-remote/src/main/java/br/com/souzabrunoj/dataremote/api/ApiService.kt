package br.com.souzabrunoj.dataremote.api

import br.com.souzabrunoj.dataremote.response.CoinResponseItem
import retrofit2.http.GET

interface ApiService {

    @GET("coins/markets")
    suspend fun getCoinQuotation(): List<CoinResponseItem>
}