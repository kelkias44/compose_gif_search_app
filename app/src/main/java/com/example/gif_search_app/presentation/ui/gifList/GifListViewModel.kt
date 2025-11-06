package com.example.gif_search_app.presentation.ui.gifList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gif_search_app.data.model.ApiResult
import com.example.gif_search_app.domain.entity.Gif
import com.example.gif_search_app.domain.usecase.GetTrendingGifsUseCase
import com.example.gif_search_app.domain.usecase.SearchGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifListViewModel @Inject constructor(
    private val searchGifsUseCase: SearchGifsUseCase,
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GifListUiState>(GifListUiState.Loading)
    val uiState: StateFlow<GifListUiState> = _uiState.asStateFlow()

    private val _gifs = MutableStateFlow<List<Gif>>(emptyList())
    val gifs: StateFlow<List<Gif>> = _gifs.asStateFlow()

    private var currentOffset = 0
    private var hasMoreGifs = true
    private var searchQuery = ""
    private var searchJob: Job? = null

    init {
        loadTrendingGifs()
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        currentOffset = 0
        hasMoreGifs = true

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            _gifs.value = emptyList()

            if (query.isBlank()) {
                loadTrendingGifs()
            } else {
                performSearch(query)
            }
        }
    }

    fun loadMoreGifs() {
        if (!hasMoreGifs || _uiState.value is GifListUiState.LoadingMore) {
            return
        }

        viewModelScope.launch {
            if (searchQuery.isBlank()) {
                loadTrendingGifs(loadMore = true)
            } else {
                performSearch(searchQuery, loadMore = true)
            }
        }
    }

    fun refresh() {
        currentOffset = 0
        hasMoreGifs = true
        _gifs.value = emptyList()

        if (searchQuery.isBlank()) {
            loadTrendingGifs()
        } else {
            viewModelScope.launch {
                performSearch(searchQuery)
            }
        }
    }

    private fun loadTrendingGifs(loadMore: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = if (loadMore) {
                GifListUiState.LoadingMore
            } else {
                GifListUiState.Loading
            }

            val result = getTrendingGifsUseCase(
                limit = 25,
                offset = currentOffset
            )
            handleResult(result, loadMore)
        }
    }

    private suspend fun performSearch(query: String, loadMore: Boolean = false) {
        _uiState.value = if (loadMore) {
            GifListUiState.LoadingMore
        } else {
            GifListUiState.Loading
        }

        val result = searchGifsUseCase(query, 25, currentOffset)
        handleResult(result, loadMore)
    }

    private fun handleResult(result: ApiResult<List<Gif>>, loadMore: Boolean) {
        when (result) {
            is ApiResult.Success -> {
                val newGifs = result.data
                _gifs.value = if (loadMore) {
                    _gifs.value + newGifs
                } else {
                    newGifs
                }
                currentOffset += newGifs.size
                hasMoreGifs = newGifs.size >= 25
                _uiState.value = GifListUiState.Success
            }
            is ApiResult.Error -> {
                _uiState.value = GifListUiState.Error(
                    result.exception.message ?: "An error occurred"
                )
            }
        }
    }
}



sealed interface GifListUiState {
    data object Loading : GifListUiState
    data object LoadingMore : GifListUiState
    data object Success : GifListUiState
    data class Error(val message: String) : GifListUiState
}