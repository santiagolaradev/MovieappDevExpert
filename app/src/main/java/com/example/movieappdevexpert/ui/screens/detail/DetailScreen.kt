package com.example.movieappdevexpert.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieappdevexpert.R
import com.example.movieappdevexpert.common.LoadingProgressIndicator
import com.example.movieappdevexpert.common.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(vm: DetailViewModel, onBack: () -> Unit) {
    val state by vm.state.collectAsState()
    val title = state.movie?.title ?: ""
    val detailState = rememberDetailState()

    detailState.ShoowMessageEffect(message = state.message) { vm.onMessageShown() }

    Screen {
        Scaffold(
            topBar = {
                DetailTopBar(
                    title,
                    detailState.scrollBehavior,
                    onBack)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { vm.onFavoriteClick()}) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = detailState.snackbarHostState)
            },
            modifier = Modifier.nestedScroll(detailState.scrollBehavior.nestedScrollConnection)
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                if (state.loading) {
                    LoadingProgressIndicator(modifier = Modifier.padding(padding))
                }

                state.movie?.let { movie ->
                    AsyncImage(
                        model = movie.backdrop,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,

                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                    )
                    Text(
                        text = movie.overview,
                        modifier = Modifier.padding(16.dp),
                    )
                    Text(
                        text = buildAnnotatedString {
                            Property("Original Language", movie.originalLanguage)
                            Property("Original Title", movie.originalTitle)
                            Property("Release date", movie.releaseDate)
                            Property("Popularity", movie.popularity.toString())
                            Property("Vote average", movie.voteAverage.toString(), end = true)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            .padding(16.dp),
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    )
}

@Composable
private fun AnnotatedString.Builder.Property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) {
            append("$name: ")
        }
        append(value)
        if (!end) {
            append("\n")
        }
    }
}