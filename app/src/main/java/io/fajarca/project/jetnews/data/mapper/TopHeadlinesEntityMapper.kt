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

    fun fromEntity(input: TopHeadlineEntity): TopHeadline {
        return TopHeadline(
            title = input.title,
            description = input.description,
            url = input.url,
            imageUrl = input.imageUrl,
            publishedAt = input.publishedAt,
            source = input.source,
            isBookmarked = input.isBookmarked
        )
    }

}