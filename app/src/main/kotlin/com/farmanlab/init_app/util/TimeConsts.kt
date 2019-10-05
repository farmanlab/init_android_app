package com.farmanlab.init_app.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

val defaultZoneId: ZoneId = ZoneId.of(ZoneId.SHORT_IDS["JST"])
fun LocalDateTime.toDefaultZonedDateTime(): ZonedDateTime = atZone(defaultZoneId)
