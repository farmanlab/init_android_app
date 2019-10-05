package com.farmanlab.init_app.data.gamedatastatus

import org.threeten.bp.ZonedDateTime
import java.util.*

data class GameDataStatus(
    val hashCommits: HashCommits,
    val lastUpdatedTime: ZonedDateTime,
    val lastUpdatedVersion: String?,
    val firstLaunchDateTime: ZonedDateTime?,
    val dataInitialized: Boolean
) {
    class HashCommits()
}
