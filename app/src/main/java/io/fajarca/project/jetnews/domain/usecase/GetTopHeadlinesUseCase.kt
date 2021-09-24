package io.fajarca.project.jetnews.domain.usecase

import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import io.fajarca.project.jetnews.util.constant.DateTimeFormat
import io.fajarca.project.jetnews.util.extension.getDifferenceInHours
import io.fajarca.project.jetnews.util.extension.toDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(): Flow<List<TopHeadline>> {
        return repository.getTopHeadlines()
            .map { headlines ->
                headlines.map { headline ->
                    headline.copy(
                        publishedAt = "${
                            headline.publishedAt.toDate(DateTimeFormat.FULL).getDifferenceInHours()
                        } jam yang lalu"
                    )
                }
            }
    }

}