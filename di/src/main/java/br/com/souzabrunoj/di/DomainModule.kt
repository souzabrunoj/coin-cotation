package br.com.souzabrunoj.di

import br.com.souzabrunoj.dataremote.repository.CoinQuotationRepositoryImpl
import br.com.souzabrunoj.dataremote.service.ApiService
import br.com.souzabrunoj.domain.repository.CoinQuotationRepository
import br.com.souzabrunoj.domain.useCase.CoinQuotationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Job())

    @Provides
    @Singleton
    fun provideCoinRepository(service: ApiService): CoinQuotationRepository = CoinQuotationRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideCoinQuotationUseCase(repository: CoinQuotationRepository, scope: CoroutineScope) = CoinQuotationUseCase(repository, scope)
}