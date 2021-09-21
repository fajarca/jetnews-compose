package io.fajarca.project.jetnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieUiState(
    val news : List<News> = emptyList(),
    val isLoading : Boolean
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(MovieUiState(isLoading = true))
    val uiState: StateFlow<MovieUiState> = _uiState

    init {
        getPosts()
    }


    private fun getPosts() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
           _uiState.update { it.copy(isLoading = false , news = repository.getNews()) }
        }
    }
}