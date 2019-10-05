package com.farmanlab.init_app.data.requestreview

import org.threeten.bp.LocalDateTime

interface RequestReviewDataSource {
    suspend fun fetchData(): RequestReviewDialogData
    suspend fun setLastConfirmedDate()
    suspend fun setAlreadyReviewed()

    data class RequestReviewDialogData(
        val lastConfirmedDate: LocalDateTime,
        val alreadyReviewed: Boolean
    )
}
