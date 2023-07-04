package br.com.souzabrunoj.dataremote.repository

import br.com.souzabrunoj.dataremote.response.mappers.toDomain
import br.com.souzabrunoj.dataremote.service.ApiService
import br.com.souzabrunoj.dataremote.utils.apiCall
import br.com.souzabrunoj.domain.model.Coin
import br.com.souzabrunoj.domain.repository.CoinQuotationRepository

class CoinQuotationRepositoryImpl(private val apiService: ApiService) : CoinQuotationRepository {

    override suspend fun getCoinQuotation(coin: String): Result<List<Coin>> = apiCall {
        apiService.getCoinQuotation(coin = coin).map { item -> item.toDomain() }
    }
}

