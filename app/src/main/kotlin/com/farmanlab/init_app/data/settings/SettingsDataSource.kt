package com.farmanlab.init_app.data.settings

import android.content.Context
import com.farmanlab.commonextensions.readFileFromAssets
import com.farmanlab.init_app.util.moshi.fromJsonArray
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

interface SettingsDataSource {

    suspend fun fetchAll(): List<Setting>

    @JsonSerializable
    data class Setting(
        @Json(name = "id")
        val id: String,
        @Json(name = "title")
        val title: String,
        @Json(name = "description")
        val description: String?,
        @Json(name = "url") val url: String
    )
}

class LocalSettingsDataSource(
    private val context: Context
) : SettingsDataSource {
    override suspend fun fetchAll(): List<SettingsDataSource.Setting> {
        val jsonBody = context.readFileFromAssets(FILE_NAME)
        return checkNotNull(fromJsonArray(jsonBody))
    }

    companion object {
        private const val FILE_NAME = "settings.json"
    }
}

