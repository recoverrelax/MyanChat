package com.lolchat.myanmarking.myanchat.di.module

import com.lolchat.myanmarking.myanchat.MyApp
import com.lolchat.myanmarking.myanchat.di.scopes.MyAppScope
import com.lolchat.myanmarking.myanchat.io.network.ApiService
import com.lolchat.myanmarking.myanchat.io.network.ApiServiceImpl
import com.lolchat.myanmarking.myanchat.io.other.DynamicUrlBuilder
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsRiotApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module
class NetworkModule {
    @Provides
    @MyAppScope
    fun providesRxAdapter(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Provides
    @MyAppScope
    fun providesHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (MyApp.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }.build()
    }

    @Provides
    @MyAppScope
    @Named("Retrofit")
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
                .build()
    }

    @Provides
    @MyAppScope
    @Named("SharedPrefs")
    fun providesMoshi2(): Moshi {
        return Moshi.Builder()
                .build()
    }

    @Provides
    @MyAppScope
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @MyAppScope
    fun providesRetrofit(rxAdapter: CallAdapter.Factory, @Named("Retrofit") moshi: Moshi, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(httpClient)
                .baseUrl("https://euw.api.riotgames.com/api/lol/EUW/")
                .build()
    }

    @Provides
    @MyAppScope
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @MyAppScope
    fun providesApiServiceImpl(dynamicUrl: DynamicUrlBuilder): ApiServiceImpl {
        return ApiServiceImpl(dynamicUrl)
    }

    @Provides
    @MyAppScope
    fun provideDynamicUrlBuilder(apiService: ApiService, apiPrefs: PrefsRiotApi): DynamicUrlBuilder {
        return DynamicUrlBuilder(apiService, apiPrefs)
    }
}