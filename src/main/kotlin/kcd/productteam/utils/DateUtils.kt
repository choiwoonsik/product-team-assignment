package kcd.productteam.utils

import java.time.OffsetDateTime
import java.time.ZoneOffset

fun getNowUTCOffsetDateTime(): OffsetDateTime {
    return OffsetDateTime.now(ZoneOffset.UTC)
}

fun OffsetDateTime.toExactlyHour(hour: Int = 0): OffsetDateTime {
    return this.withHour(hour).withMinute(0).withSecond(0).withNano(0)
}

fun OffsetDateTime.getYmd(): String {
    return "${this.year}-${this.monthValue}-${this.dayOfMonth}"
}