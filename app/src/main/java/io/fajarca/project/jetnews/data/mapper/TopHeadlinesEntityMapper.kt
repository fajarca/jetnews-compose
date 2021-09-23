package io.fajarca.project.jetnews.data.mapper

import io.fajarca.project.jetnews.data.db.TopHeadlineEntity
import io.fajarca.project.jetnews.data.response.TopHeadlinesDto
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject

class TopHeadlinesEntityMapper @Inject constructor() {

    fun toEntity(input: TopHeadlinesDto): List<TopHeadlineEntity> {
        return input.articles.map {
            TopHeadlineEntity(
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                imageUrl = it.urlToImage.orEmpty(),
                publishedAt = it.publishedAt.orEmpty(),
                source = it.source?.name.orEmpty(),
                isBookmarked = false
            )
        }
    }

    fun fromEntity(input: List<TopHeadlineEntity>): List<TopHeadline> {
        return input.map {
            TopHeadline(
                title = it.title,
                description = it.description,
                url = it.url,
                imageUrl = it.imageUrl,
                publishedAt = it.publishedAt,
                source = it.source,
                isBookmarked = it.isBookmarked
            )
        }

    }

}