package io.fajarca.project.jetnews.data.db.converter

import androidx.room.TypeConverter
import java.util.*

class DatabaseTypeConverter {

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value == 1
    }

    @TypeConverter
    fun fromBoolean(value : Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}