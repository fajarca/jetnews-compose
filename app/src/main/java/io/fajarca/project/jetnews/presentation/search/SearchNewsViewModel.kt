package io.fajarca.project.jetnews.presentation.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import io.fajarca.project.jetnews.domain.usecase.SearchNewsUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    fun searchNews(query : String, language : String): Flow<PagingData<TopHeadline>> {
        return searchNewsUseCase.execute(query, language)
    }

}