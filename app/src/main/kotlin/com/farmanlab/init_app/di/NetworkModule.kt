package com.farmanlab.init_app.di

import com.farmanlab.init_app.BuildConfig
import com.farmanlab.init_app.util.moshi.moshi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.multiton
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = Kodein.Module("network") {
    bind<OkHttpClient>() with singleton { getOkHttpClient() }
    bind<Retrofit>() with multiton { baseUrl: String ->
        getRetrofit(
            okHttpClient = instance(),
            baseUrl = baseUrl
        )
    }
}

private fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
    }
    .build()

private fun getRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .build()
