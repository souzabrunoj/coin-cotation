package br.com.souzabrunoj.domain.repository

import br.com.souzabrunoj.domain.model.Coin

interface CoinQuotationRepository {

    suspend fun getCoinQuotation(coin: String): Result<List<Coin>>
}