package com.farmanlab.init_app.util.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class ZonedDateTimeAdapter {

    @ToJson
    fun toJson(dateTime: ZonedDateTime): String {
        return dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }

    @FromJson
    fun fromJson(string: String): ZonedDateTime {
        return ZonedDateTime.parse(string, DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }
}
