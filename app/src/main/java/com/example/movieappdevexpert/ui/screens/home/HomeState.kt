package com.example.movieappdevexpert.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.movieappdevexpert.common.PermissionRequestEffect

@OptIn(ExperimentalMaterial3Api::class)
class HomeState(
    val scrollBehavior: TopAppBarScrollBehavior
)
{

    @Composable
    fun AskLocationEffect(onPermissionResult: (Boolean) -> Unit) {
        PermissionRequestEffect(permission = android.Manifest.permission.ACCESS_COARSE_LOCATION) { isGranted ->
            onPermissionResult(isGranted)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberHomeState(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
): HomeState {

    return remember(scrollBehavior) { HomeState(scrollBehavior) }
}