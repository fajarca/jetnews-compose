package io.fajarca.project.jetnews.domain.usecase

import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository){

    suspend fun execute(): Either<Exception, List<TopHeadline>> {
        return repository.getTopHeadlines()
    }

}