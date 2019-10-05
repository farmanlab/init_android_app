package com.farmanlab.init_app.data.requestreview

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class SharedPreferenceRequestReviewDataSource(context: Context) :
    RequestReviewDataSource {
    override suspend fun setLastConfirmedDate() {
        pref.edit {
            putLong(KEY_LAST_CONFIRMED_DATE, System.currentTimeMillis())
        }
    }

    override suspend fun setAlreadyReviewed() {
        pref.edit {
            putBoolean(KEY_ALREADY_REVIEWED, true)
        }
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override suspend fun fetchData(): RequestReviewDataSource.RequestReviewDialogData =
        RequestReviewDataSource.RequestReviewDialogData(
            lastConfirmedDate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(pref.getLong(KEY_LAST_CONFIRMED_DATE, 0L)),
                ZoneId.systemDefault()
            ),
            alreadyReviewed = pref.getBoolean(KEY_ALREADY_REVIEWED, false)
        )


    companion object {
        private const val KEY_LAST_CONFIRMED_DATE = "last_confirmed_date"
        private const val KEY_ALREADY_REVIEWED = "already_reviewed"

        private const val PREF_NAME = "review_dialog"
    }
}
