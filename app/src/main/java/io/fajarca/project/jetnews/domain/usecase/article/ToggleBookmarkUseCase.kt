package io.fajarca.project.jetnews.domain.usecase.article

import io.fajarca.project.jetnews.domain.repository.NewsRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(private val repository: NewsRepository){

    suspend fun execute(title : String): Int {
        return repository.toggleBookmark(title)
    }

}