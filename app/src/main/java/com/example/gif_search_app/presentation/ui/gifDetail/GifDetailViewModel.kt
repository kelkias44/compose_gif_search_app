package com.example.gif_search_app.presentation.ui.gifDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.gif_search_app.domain.entity.Gif
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GifDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

}