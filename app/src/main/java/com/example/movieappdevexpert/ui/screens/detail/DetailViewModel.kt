package com.example.movieappdevexpert.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappdevexpert.data.Movie
import com.example.movieappdevexpert.data.MoviesClient
import com.example.movieappdevexpert.data.MoviesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val id: Int): ViewModel() {


    private val repository = MoviesRepository(MoviesClient.instance)
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
    )

    sealed interface UiEvent{
        data class ShowMessage(val message: String): UiEvent
    }

    private val _events = Channel<UiEvent>()
    val events: Flow<UiEvent> = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, movie = repository.findMovieById(id))
        }
    }

    fun onFavoriteClick() {
        _events.trySend(UiEvent.ShowMessage("Favorite clicked"))
    }
}
