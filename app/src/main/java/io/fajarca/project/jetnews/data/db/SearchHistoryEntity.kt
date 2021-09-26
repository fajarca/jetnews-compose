package io.fajarca.project.jetnews.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "search_histories")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "query") val query: String,
    @ColumnInfo(name = "created_at") val createdAt: Date
)