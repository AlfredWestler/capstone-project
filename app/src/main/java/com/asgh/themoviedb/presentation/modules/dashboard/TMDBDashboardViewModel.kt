package com.asgh.themoviedb.presentation.modules.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asgh.themoviedb.R
import com.asgh.themoviedb.commons.either.FailureModel
import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.use_case.TMDBLatestUseCase
import com.asgh.themoviedb.domain.use_case.TMDBNowPlayingUseCase
import com.asgh.themoviedb.domain.use_case.TMDBTopRatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBDashboardViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val nowPlayingUseCase: TMDBNowPlayingUseCase,
    private val topRatedUseCase: TMDBTopRatedUseCase,
    private val latestUseCase: TMDBLatestUseCase,
): ViewModel() {

    private val _nowPlayingState = MutableStateFlow<TMDBEither<List<TMDBMovie>, FailureModel>>(TMDBEither.Loading)
    val nowPlayingState : StateFlow<TMDBEither<List<TMDBMovie>, FailureModel>> get() = _nowPlayingState
    private val _topRatedState = MutableStateFlow<TMDBEither<List<TMDBMovie>, FailureModel>>(TMDBEither.Loading)
    val topRatedState: StateFlow<TMDBEither<List<TMDBMovie>, FailureModel>> get() = _topRatedState
    private val _latestState = MutableStateFlow<TMDBEither<TMDBLatestMovie, FailureModel>>(TMDBEither.Loading)
    val latestState: StateFlow<TMDBEither<TMDBLatestMovie, FailureModel>> get() = _latestState

    private val _internetConnectionEnabled = mutableStateOf(false)
    val internetConnectionEnabled: State<Boolean> get() = _internetConnectionEnabled
    private val firstTime = mutableStateOf(true)

    private val _toolbarTitle = MutableLiveData("")
    val toolbarTitle: LiveData<String> get() = _toolbarTitle

    fun setToolbarTitle(value: String) {
        _toolbarTitle.value = value
    }

    fun setInternetConnectionEnabled(value: Boolean) {
        if(value != _internetConnectionEnabled.value || firstTime.value){
            _internetConnectionEnabled.value = value
            if(hasToConsume()) realGetAll()
        }
    }
    private fun hasToConsume():Boolean {
        return if(firstTime.value) {
            firstTime.value = false
            true
        } else _internetConnectionEnabled.value
    }
    fun randomItem(list: List<TMDBMovie>): TMDBMovie {
        return if(list.isNotEmpty()) {
            list.random()
        } else TMDBMovie()
    }
    fun randomBackdrop(list: List<TMDBMovie>): String {
        return if(list.isNotEmpty()) {
            val randomData = list.random()
            randomData.backdropPath
        } else ""
    }
    private fun realGetAll() {
        viewModelScope.launch(dispatcher) {
            val nowPlaying = async { getNowPlayingMovies() }
            val topRated = async { getTopRatedMovies() }
            val latest = async { getLatestMovies() }
            nowPlaying.await()
            topRated.await()
            latest.await()
        }
    }
    private fun getNowPlayingMovies() {
        nowPlayingUseCase.getNowPlayingMoviesModified().onEach { result ->
            result.apply {
                onLoading { _nowPlayingState.value = (TMDBEither.Loading) }
                onFailure { _nowPlayingState.value = (TMDBEither.Failure(FailureModel(
                    R.string.generic_error_message
                ))) }
                onSuccess { _nowPlayingState.value = (TMDBEither.Success(it)) }
            }
        }.launchIn(viewModelScope)
    }
    private fun getTopRatedMovies() {
        topRatedUseCase.getTopRatedMoviesAsFlow().onEach { result ->
            result.apply {
                onLoading { _topRatedState.value = (TMDBEither.Loading) }
                onFailure { _topRatedState.value = (TMDBEither.Failure(FailureModel(
                    R.string.generic_error_message
                ))) }
                onSuccess { _topRatedState.value = (TMDBEither.Success(it)) }
            }
        }.launchIn(viewModelScope)
    }

    private fun getLatestMovies() {
        latestUseCase.getLatestAsFlow().onEach { result ->
            result.apply {
                onLoading { _latestState.value = (TMDBEither.Loading) }
                onFailure { _latestState.value = (TMDBEither.Failure(FailureModel(
                    R.string.generic_error_message
                ))) }
                onSuccess { _latestState.value = (TMDBEither.Success(it)) }
            }
        }.launchIn(viewModelScope)
    }
}