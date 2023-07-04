package br.com.souzabrunoj.di

import android.content.Context
import br.com.souzabrunoj.dataremote.service.ApiService
import br.com.souzabrunoj.dataremote.factory.ServiceClientFactory
import br.com.souzabrunoj.dataremote.factory.ServiceClientFactory.createClient
import br.com.souzabrunoj.dataremote.utils.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataRemoteModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient = ServiceClientFactory.createOkHttpClient(context)

    @Provides
    @Singleton
    fun provideAdapterFactor(): CoroutineCallAdapterFactory = CoroutineCallAdapterFactory()

    @Provides
    @Singleton
    fun provideService(
        okhttpClient: OkHttpClient, adapter: CoroutineCallAdapterFactory
    ): ApiService = createClient(BASE_URL, okhttpClient, adapter)


}