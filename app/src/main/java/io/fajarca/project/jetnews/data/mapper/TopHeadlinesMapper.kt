package io.fajarca.project.jetnews.data.mapper

import io.fajarca.project.jetnews.data.response.TopHeadlinesDto
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject

class TopHeadlinesMapper @Inject constructor() {

    fun map(input: TopHeadlinesDto): List<TopHeadline> {
        return input.articles?.map {
            TopHeadline(
                it?.description.orEmpty(),
                it?.publishedAt.orEmpty(),
                it?.title.orEmpty(),
                it?.url.orEmpty(),
                it?.urlToImage.orEmpty()
            )
        } ?: emptyList()
    }

}