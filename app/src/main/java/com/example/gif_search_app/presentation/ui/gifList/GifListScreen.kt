package com.example.gif_search_app.presentation.ui.gifList

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.gif_search_app.R
import com.example.gif_search_app.domain.entity.Gif

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifGridScreen(
    onGifClick: (Gif) -> Unit
) {
    val viewModel: GifListViewModel = hiltViewModel()
    val gifs by viewModel.gifs.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val gridState = rememberLazyGridState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            val lastVisibleItem = visibleItemsInfo.lastOrNull()

            lastVisibleItem != null &&
                    lastVisibleItem.index >= layoutInfo.totalItemsCount - 5
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            viewModel.loadMoreGifs()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.gifSearch)) }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
            SearchBar(
                onQueryChange = viewModel::onSearchQueryChanged,
                modifier = Modifier.fillMaxWidth()
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = uiState) {
                    is GifListUiState.Loading -> {
                        CenterLoadingIndicator()
                    }

                    is GifListUiState.LoadingMore -> {
                        GifGrid(
                            gifs = gifs,
                            onGifClick = onGifClick,
                            gridState = gridState
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(40.dp))
                        }
                    }

                    is GifListUiState.Success -> {
                        if (gifs.isEmpty()) {
                            EmptyState()
                        } else {
                            GifGrid(
                                gifs = gifs,
                                onGifClick = onGifClick,
                                gridState = gridState
                            )
                        }
                    }

                    is GifListUiState.Error -> {
                        ErrorState(
                            message = state.message,
                            onRetry = { viewModel.refresh() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GifGrid(
    gifs: List<Gif>,
    onGifClick: (Gif) -> Unit,
    gridState: LazyGridState,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val columns = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = gridState,
        modifier = modifier.fillMaxSize()
    ) {
        items(gifs) { gif ->
            GifGridItem(
                gif = gif,
                onClick = { onGifClick(gif) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = { newQuery ->
            query = newQuery
            onQueryChange(newQuery)
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = stringResource(R.string.gifSearch))
        },
        placeholder = { Text(stringResource(R.string.searchGif)) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun CenterLoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
    }
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.noGifFound),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ErrorState(
    message: String,
    onRetry: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.error),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            onRetry?.let {
                Button(onClick = it) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
    }
}