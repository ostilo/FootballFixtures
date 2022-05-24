package com.oze.footballfixtures.presentation.di

import com.oze.footballfixtures.presentation.di.remote.ApiService
import com.oze.footballfixtures.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val REQUEST_TIMEOUT = 10
    private var okHttpClient: OkHttpClient? = null

    @[Provides]
    internal fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /*
    * The method returns the Okhttp object
    * */
    @[Provides]
    internal fun provideOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor{chain ->
                val request = chain.request().newBuilder().addHeader(
                    "Connection",
                    "close"
                ).build()
                chain.proceed(request)
            }
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()

        return (okHttpClient)!!
    }

    /*
    * The method returns the Retrofit object
    * */
    @[Provides]
    internal fun provideRetrofit(okHttpClient: OkHttpClient,   gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            //  .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @[Provides]
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * The method returns the Gson object
     **/

    @[Provides]
    fun getGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
//    @Provides
//    @Singleton
//    fun provideMoshi(): Moshi {
//        return Moshi.Builder()
//            .addLast(KotlinJsonAdapterFactory())
//            .build()
//    }
}