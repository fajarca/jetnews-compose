package io.fajarca.project.jetnews.data.mapper

import io.fajarca.project.jetnews.data.response.TopHeadlinesDto
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject

class TopHeadlinesMapper @Inject constructor() {

    fun map(input: TopHeadlinesDto.Article): TopHeadline {
        return TopHeadline(
            title = input.title.orEmpty(),
            description = input.description.orEmpty(),
            url = input.url.orEmpty(),
            imageUrl = input.urlToImage.orEmpty(),
            publishedAt = input.publishedAt.orEmpty(),
            source = input.source?.name.orEmpty(),
            isBookmarked = false
        )
    }

}