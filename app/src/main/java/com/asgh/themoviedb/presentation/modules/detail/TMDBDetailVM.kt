package com.asgh.themoviedb.presentation.modules.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asgh.themoviedb.data.mapper.toGenre
import com.example.local.relation.TMDBMoviesInGenre
import com.asgh.themoviedb.domain.model.TMDBGenre
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.use_case.TMDBGenreUseCase
import com.asgh.themoviedb.domain.use_case.TMDBMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TMDBDetailVM @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val useCase: TMDBGenreUseCase,
    private val movieUseCase: TMDBMovieUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var movieId = savedStateHandle.get<String>("movie")

    private val _selectedMovie = mutableStateOf(TMDBMovie())
    val selectedMovie : State<TMDBMovie> get() = _selectedMovie
    private val _movieGenres = mutableStateOf("")
    val movieGenres : State<String> get() = _movieGenres
    private val _genresList = mutableStateOf(emptyList<TMDBGenre>())
    private val _relatedMovies = mutableStateListOf<TMDBMoviesInGenre>()
    val relatedMovies : SnapshotStateList<TMDBMoviesInGenre> get() = _relatedMovies
    private val _isLoading = mutableStateOf(false)
    val isLoading : State<Boolean> get() = _isLoading

    fun getMovieById(id: Int) {
        viewModelScope.launch(dispatcher) {
            movieUseCase(id).collect { result ->
                result.onLoading { _isLoading.value = true }
                result.onSuccess {
                    _isLoading.value = false
                    setSelectedMovie(it)
                }
            }
        }
    }
    fun setSelectedMovie(movie: TMDBMovie) {
        _selectedMovie.value = movie
        _movieGenres.value = ""
        _relatedMovies.clear()
        getAdditionalInfo()
    }

    private fun getAdditionalInfo() {
        viewModelScope.launch {
            getInfoAsync()
        }
    }

    private suspend fun getInfoAsync() {
        getGenres()
        val related = coroutineScope {
            _genresList.value.map {
                async {
                    useCase.getMoviesInGenre(it.id)
                }
            }
        }
        related.awaitAll().forEach { list ->
            list.collect { result ->
                result.onSuccess { _relatedMovies.addAll(it) }
            }
        }
    }

    private suspend fun getGenres() {
        useCase.getMovieWithGenres(_selectedMovie.value.id).collect { result ->
            result.onFailure { _movieGenres.value = "" }
            result.onSuccess {
                _genresList.value = it.genres.map { genres -> genres.toGenre() }
                _movieGenres.value = it.getGenresString()
            }
        }
    }
}