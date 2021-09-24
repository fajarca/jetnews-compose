package io.fajarca.project.jetnews.util.extension

import io.fajarca.project.jetnews.util.constant.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(timeFormat: DateTimeFormat): Date {
    val simpleDateFormat = SimpleDateFormat(timeFormat.value, Locale.US)
    simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
    return simpleDateFormat.parse(this) ?: Date()
}
