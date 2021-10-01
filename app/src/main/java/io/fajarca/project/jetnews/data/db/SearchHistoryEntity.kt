package io.fajarca.project.jetnews.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "search_histories")
data class SearchHistoryEntity(
    @PrimaryKey @ColumnInfo(name = "query") val query: String,
    @ColumnInfo(name = "created_at") val createdAt: Date
)