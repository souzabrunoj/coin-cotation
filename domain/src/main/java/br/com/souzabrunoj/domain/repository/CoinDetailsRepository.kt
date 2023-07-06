package br.com.souzabrunoj.domain.repository

import br.com.souzabrunoj.domain.model.CoinDetails

interface CoinDetailsRepository {

    suspend fun getCoinDetails(id: String): Result<CoinDetails>
}