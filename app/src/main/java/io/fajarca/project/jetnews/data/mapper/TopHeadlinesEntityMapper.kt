package io.fajarca.project.jetnews.data.mapper

import io.fajarca.project.jetnews.data.db.TopHeadlineEntity
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject

class TopHeadlinesEntityMapper @Inject constructor() {

    fun map(input: List<TopHeadline>): List<TopHeadlineEntity> {
        return input.map {
            TopHeadlineEntity(
                title = it.title,
                description = it.description,
                url = it.url,
                imageUrl = it.imageUrl,
                publishedAt = it.publishedAt
            )
        }

    }

}