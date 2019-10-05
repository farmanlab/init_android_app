package com.farmanlab.init_app.domain.gamedata

interface GameDataUpdateUseCase {
    suspend fun updateData(
        request: RequestParams
    )

    data class RequestParams(
        val apiToken: String
    )
}
