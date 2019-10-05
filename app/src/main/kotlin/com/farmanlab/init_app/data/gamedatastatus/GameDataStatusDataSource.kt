package com.farmanlab.init_app.data.gamedatastatus

import android.content.Context
import androidx.core.content.edit
import com.farmanlab.init_app.BuildConfig
import com.farmanlab.init_app.util.defaultZoneId
import com.farmanlab.init_app.util.toDefaultZonedDateTime
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime

interface GameDataStatusDataSource {
    suspend fun fetchStatus(): GameDataStatus
    suspend fun setHashCommits(hashCommits: GameDataStatus.HashCommits)
    suspend fun setLastUpdatedDateTime(now: LocalDateTime)
    suspend fun setLastUpdatedVersion()
    suspend fun setDataInitialized()
}

class SharedPreferenceGameDataStatusDataSource(private val context: Context) : GameDataStatusDataSource {
    private val pref = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    override suspend fun fetchStatus(): GameDataStatus = GameDataStatus(
        hashCommits = GameDataStatus.HashCommits(),
        lastUpdatedTime = pref.getLong(KEY_LAST_UPDATED_DATA, VALUE_NOT_SET).let {
            ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(it),
                defaultZoneId
            )
        },
        lastUpdatedVersion = pref.getString(KEY_LAST_UPDATED_VERSION, null),
        firstLaunchDateTime = pref.getLong(KEY_FIRST_LAUNCH_DATE_TIME, VALUE_NOT_SET).let {
            if (it == VALUE_NOT_SET) return@let null
            ZonedDateTime.ofInstant(Instant.ofEpochMilli(it), defaultZoneId)
        },
        dataInitialized = pref.getBoolean(KEY_DATA_INITIALIZED, false)
    )

    override suspend fun setDataInitialized() {
        pref.edit {
            putBoolean(KEY_DATA_INITIALIZED, true)
        }
    }

    override suspend fun setLastUpdatedDateTime(now: LocalDateTime) {
        pref.edit {
            putLong(
                KEY_LAST_UPDATED_DATA,
                now.toDefaultZonedDateTime().toEpochSecond()
            )
        }
    }

    override suspend fun setHashCommits(hashCommits: GameDataStatus.HashCommits) {
        pref.edit {
        }
    }

    override suspend fun setLastUpdatedVersion() {
        pref.edit {
            putString(KEY_LAST_UPDATED_VERSION, BuildConfig.VERSION_NAME)
        }
    }

    companion object {
        private const val KEY_LAST_UPDATED_DATA = "last_updated_data"
        private const val KEY_LAST_UPDATED_VERSION = "last_updated_version"
        private const val KEY_FIRST_LAUNCH_DATE_TIME = "first_launch_date_time"
        private const val KEY_DATA_INITIALIZED = "data_initialized"

        private const val VALUE_NOT_SET = -1L
    }
}
