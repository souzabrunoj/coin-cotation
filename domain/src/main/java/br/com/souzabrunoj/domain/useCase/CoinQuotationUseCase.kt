package br.com.souzabrunoj.domain.useCase

import br.com.souzabrunoj.domain.model.Coin
import br.com.souzabrunoj.domain.repository.CoinQuotationRepository
import kotlinx.coroutines.CoroutineScope

class CoinQuotationUseCase(private val repository: CoinQuotationRepository, override val scope: CoroutineScope?) : UseCase<String, List<Coin>>() {
    override suspend fun call(params: String): Result<List<Coin>> = repository.getCoinQuotation(params)
}