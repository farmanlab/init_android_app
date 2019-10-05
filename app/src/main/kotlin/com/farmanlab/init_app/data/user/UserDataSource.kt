package com.farmanlab.init_app.data.user

import android.content.Context
import androidx.core.content.edit
import com.farmanlab.init_app.util.defaultZoneId
import com.farmanlab.init_app.util.toDefaultZonedDateTime
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime

interface UserDataSource {
    suspend fun fetchUserData(): UserData
    suspend fun setFirstLaunchDateTime(now: LocalDateTime)
}

class SharedPreferenceUserDataSource(private val context: Context) : UserDataSource {
    private val pref = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    override suspend fun fetchUserData(): UserData = UserData(
        firstLaunchDateTime = pref.getLong(KEY_FIRST_LAUNCH_DATE_TIME, VALUE_NOT_SET).let {
            if (it == VALUE_NOT_SET) return@let null
            ZonedDateTime.ofInstant(Instant.ofEpochMilli(it), defaultZoneId)
        }
    )

    override suspend fun setFirstLaunchDateTime(now: LocalDateTime) {
        pref.edit {
            putLong(
                KEY_FIRST_LAUNCH_DATE_TIME,
                now.toDefaultZonedDateTime().toEpochSecond()
            )
        }
    }

    companion object {
        private const val KEY_FIRST_LAUNCH_DATE_TIME = "first_launch_date_time"
        private const val VALUE_NOT_SET = -1L
    }
}
