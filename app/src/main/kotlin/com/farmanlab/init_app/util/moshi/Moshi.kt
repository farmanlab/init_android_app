package com.farmanlab.init_app.util.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import se.ansman.kotshi.KotshiJsonAdapterFactory
import timber.log.Timber

val moshi: Moshi = Moshi.Builder()
    .add(ZonedDateTimeAdapter())
    .add(ApplicationJsonAdapterFactory.INSTANCE)
    .build()

inline fun <reified T> fromJson(json: String): T? =
    try {
        moshi.adapter(T::class.java).fromJson(json)
    } catch (e: Exception) {
        Timber.e(e)
        throw e
    }

inline fun <reified T> fromJsonArray(json: String): List<T>? =
    try {
        moshi.adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).fromJson(json)
    } catch (e: Exception) {
        Timber.e(e)
        throw e
    }

@KotshiJsonAdapterFactory
abstract class ApplicationJsonAdapterFactory : JsonAdapter.Factory {
    companion object {
        val INSTANCE: ApplicationJsonAdapterFactory = KotshiApplicationJsonAdapterFactory
    }
}
