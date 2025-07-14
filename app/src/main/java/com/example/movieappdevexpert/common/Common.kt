package com.example.movieappdevexpert.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.movieappdevexpert.ui.theme.MovieappDevExpertTheme

@Composable
fun Screen(context: @Composable () -> Unit) {
    MovieappDevExpertTheme  {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = context
        )
    }
}

@Composable
fun LoadingProgressIndicator(modifier: Modifier){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}