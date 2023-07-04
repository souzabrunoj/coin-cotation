package br.com.souzabrunoj.di

import br.com.souzabrunoj.dataremote.repository.CoinQuotationRepositoryImpl
import br.com.souzabrunoj.dataremote.service.ApiService
import br.com.souzabrunoj.domain.repository.CoinQuotationRepository
import br.com.souzabrunoj.domain.useCase.CoinQuotationUseCase
import br.com.souzabrunoj.domain.useCase.CoinQuotationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideCoinRepository(service: ApiService): CoinQuotationRepository {
        return CoinQuotationRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideCoinQuotationUseCase(repository: CoinQuotationRepository): CoinQuotationUseCase {
        return CoinQuotationUseCaseImpl(repository)
    }
}