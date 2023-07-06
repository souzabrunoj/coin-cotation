package br.com.souzabrunoj.domain.useCase.details

import br.com.souzabrunoj.domain.model.CoinDetails

interface CoinDetailsUseCase {
    suspend fun getCryptoDetails(id: String): Result<CoinDetails>
}