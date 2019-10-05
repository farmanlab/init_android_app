package com.farmanlab.init_app.data.gamedata

import com.squareup.moshi.Json
import kotlinx.coroutines.Deferred
import org.threeten.bp.ZonedDateTime
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import se.ansman.kotshi.JsonSerializable

interface GameDataDataSource {
    suspend fun fetch(request: RequestParams): GameData

    @JsonSerializable
    data class GameData(
        @Json(name = "contents")
        val contents: Contents?,
        @Json(name = "hash_commits")
        val hashCommits: HashCommits?
    ) {
        class Contents

        class HashCommits
    }

    data class RequestParams(
        val apiToken: String
    ) {
        data class ApiStatus(
            val commitHash: String
        )
    }
}

class RetrofitGameDataDataSource(private val retrofit: Retrofit) : GameDataDataSource {

    interface ApiService {
        // FIXME
        @GET("macros/s/xxxxxxxx/exec")
        fun getAsync(
            @Query("api_token") token: String
        ): Deferred<GameDataDataSource.GameData>
    }

    override suspend fun fetch(request: GameDataDataSource.RequestParams): GameDataDataSource.GameData =
        retrofit.create(ApiService::class.java)
            .getAsync(
                token = request.apiToken
            )
            .await()
}
