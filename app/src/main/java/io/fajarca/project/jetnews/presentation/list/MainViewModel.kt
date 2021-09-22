package io.fajarca.project.jetnews.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.domain.usecase.GetTopHeadlinesUseCase
import io.fajarca.project.jetnews.infrastructure.coroutine.CoroutineDispatcherProvider
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState(isLoading = true))
    val uiState: StateFlow<MovieUiState> = _uiState

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            _uiState.update {
                val topHeadlines = getTopHeadlinesUseCase.execute()
                when (topHeadlines) {
                    is Either.Left -> it.copy(emptyList(), false)
                    is Either.Right -> it.copy(topHeadlines.data, false)
                }
            }
        }
    }
}