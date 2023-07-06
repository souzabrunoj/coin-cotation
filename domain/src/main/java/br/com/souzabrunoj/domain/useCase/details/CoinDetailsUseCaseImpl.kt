package br.com.souzabrunoj.domain.useCase.details

import br.com.souzabrunoj.domain.model.CoinDetails
import br.com.souzabrunoj.domain.repository.CoinDetailsRepository

class CoinDetailsUseCaseImpl(private val repository: CoinDetailsRepository) : CoinDetailsUseCase {

    override suspend fun getCryptoDetails(id: String): Result<CoinDetails> =  repository.getCoinDetails(id)

}