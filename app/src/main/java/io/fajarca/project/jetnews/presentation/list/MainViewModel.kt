package io.fajarca.project.jetnews.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.usecase.GetNewsSourceUseCase
import io.fajarca.project.jetnews.domain.usecase.GetTopHeadlinesUseCase
import io.fajarca.project.jetnews.infrastructure.coroutine.CoroutineDispatcherProvider
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getNewsSourceUseCase: GetNewsSourceUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState(isLoading = true))
    val uiState: StateFlow<MovieUiState> = _uiState

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            getTopHeadlinesUseCase.execute().collect { headlines ->
                _uiState.update { uiState ->
                    uiState.copy(topHeadlines = headlines, isLoading = false)
                }
            }

        }
    }

    fun getNewsSource() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            getNewsSourceUseCase.execute().collect { sources ->
                _uiState.update { uiState -> uiState.copy(isLoading = false, newsSource = sources) }
            }

        }
    }
}