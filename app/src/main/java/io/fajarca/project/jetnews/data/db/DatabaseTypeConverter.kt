package io.fajarca.project.jetnews.data.db

import androidx.room.TypeConverter

class DatabaseTypeConverter {

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value == 1
    }

    @TypeConverter
    fun fromBoolean(value : Boolean): Int {
        return if (value) 1 else 0
    }

}