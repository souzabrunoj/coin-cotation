package br.com.souzabrunoj.domain.useCase

import br.com.souzabrunoj.domain.model.Coin

interface CoinQuotationUseCase {
    suspend fun getCryptoQuotation(coin: String): Result<List<Coin>>
}