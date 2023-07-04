package br.com.souzabrunoj.dataremote.factory

import android.content.Context
import br.com.souzabrunoj.dataremote.utils.ConnectivityInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 30L

object ServiceClientFactory {

    inline fun <reified T> createClient(url: String, client: OkHttpClient?, adapter: CoroutineCallAdapterFactory): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addClient(client)
            .addConverterFactory(UnitConverterFactory)
            .addCallAdapterFactory(adapter)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(T::class.java)
    }

    fun Retrofit.Builder.addClient(okHttpClient: OkHttpClient?): Retrofit.Builder =
        when (okHttpClient != null) {
            true -> client(okHttpClient)
            else -> this
        }

    fun createOkHttpClient(context: Context): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        with(clientBuilder) {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        }

        clientBuilder
            .addInterceptor(createHttpLoggingInterceptor())
            .addInterceptor(ConnectivityInterceptor(context))

        return clientBuilder.build()
    }

    private fun createHttpLoggingInterceptor() = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        this
    }

    object UnitConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *>? = if (type == Unit::class.java) UnitConverter else null

        private object UnitConverter : Converter<ResponseBody, Unit> {
            override fun convert(value: ResponseBody) {
                value.close()
            }
        }
    }
}


