package io.fajarca.project.jetnews.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_headlines")
data class TopHeadlineEntity(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "bookmark") val isBookmarked : Boolean
)