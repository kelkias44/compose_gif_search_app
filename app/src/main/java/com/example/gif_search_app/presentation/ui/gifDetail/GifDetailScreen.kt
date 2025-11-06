package com.example.gif_search_app.presentation.ui.gifDetail

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gif_search_app.domain.entity.Gif
import com.example.gif_search_app.presentation.ui.gifList.ErrorState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.gif_search_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifDetailScreen(
    onBackClick: () -> Unit,
    gif: Gif?,
) {
    val viewModel: GifDetailViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.gifDetails)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { paddingValues ->
        if (gif != null) {
                    GifDetailContent(gif, Modifier.padding(paddingValues))
                } else{
                    ErrorState(
                    message = stringResource(R.string.gifNotFound),
                    onRetry = null,
                    modifier = Modifier.padding(paddingValues)
                )
            }
    }
}

@Composable
fun GifDetailContent(
    gif: Gif,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val gifEnabledLoader = ImageLoader.Builder(context)
        .components {
            if ( SDK_INT >= 28 ) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
            AsyncImage(
                model = gif.images.previewGif.url,
                contentDescription = gif.title,
                placeholder = painterResource(R.drawable.ic_downloading),
                imageLoader = gifEnabledLoader,
                modifier = Modifier
                    .height(480.dp)
            )

        Text(
            text = gif.title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRow(stringResource(R.string.id), gif.id)
                InfoRow(stringResource(R.string.type), gif.type)
                InfoRow(stringResource(R.string.height), "${gif.images.previewGif.height}px")
                InfoRow(stringResource(R.string.width), "${gif.images.previewGif.width}px")
                InfoRow(stringResource(R.string.rating), gif.rating)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}