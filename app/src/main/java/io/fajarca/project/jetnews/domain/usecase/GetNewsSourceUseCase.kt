package io.fajarca.project.jetnews.domain.usecase

import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetNewsSourceUseCase @Inject constructor(private val repository: NewsRepository){

    suspend fun execute(): Flow<List<String>> {
        return repository.getNewsSource()
    }

}