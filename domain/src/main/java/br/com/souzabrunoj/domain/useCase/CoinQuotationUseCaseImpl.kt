package br.com.souzabrunoj.domain.useCase

import br.com.souzabrunoj.domain.model.Coin
import br.com.souzabrunoj.domain.repository.CoinQuotationRepository

class CoinQuotationUseCaseImpl(private val repository: CoinQuotationRepository) : CoinQuotationUseCase {

    override suspend fun getCryptoQuotation(coin: String): Result<List<Coin>> = repository.getCoinQuotation(coin)

}