package br.com.souzabrunoj.dataremote.repository

import br.com.souzabrunoj.dataremote.response.mappers.toDomain
import br.com.souzabrunoj.dataremote.service.ApiService
import br.com.souzabrunoj.dataremote.utils.safeApiCall
import br.com.souzabrunoj.domain.model.CoinDetails
import br.com.souzabrunoj.domain.repository.CoinDetailsRepository

class CoinDetailsRepositoryImpl(private val apiService: ApiService) : CoinDetailsRepository {

    override suspend fun getCoinDetails(id: String): Result<CoinDetails> = safeApiCall { apiService.getCoinDetails(id = id) }.map { it.toDomain() }
}




