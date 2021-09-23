package io.fajarca.project.jetnews.domain.usecase

import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ToggleBookmarkUseCase @Inject constructor(private val repository: NewsRepository){

    suspend fun execute(title : String): Int {
        return repository.toggleBookmark(title)
    }

}