package com.asgh.themoviedb.presentation.modules.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.commons.either.FailureModel
import com.asgh.themoviedb.commons.either.ServiceState
import com.asgh.themoviedb.commons.either.isSuccess
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.use_case.TMDBLatestUseCase
import com.asgh.themoviedb.domain.use_case.TMDBNowPlayingUseCase
import com.asgh.themoviedb.domain.use_case.TMDBTopRatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBDashboardViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val nowPlayingUseCase: TMDBNowPlayingUseCase,
    private val topRatedUseCase: TMDBTopRatedUseCase,
    private val latestUseCase: TMDBLatestUseCase
): ViewModel() {

    private val _nowPlayingState = MutableLiveData<ServiceState<List<TMDBMovie>>>()
    val nowPlayingState: LiveData<ServiceState<List<TMDBMovie>>> get() = _nowPlayingState
    private val _topRatedState = MutableLiveData<ServiceState<List<TMDBMovie>>>()
    val topRatedState: LiveData<ServiceState<List<TMDBMovie>>> get() = _topRatedState
    private val _latestState = MutableLiveData<ServiceState<TMDBLatestMovie>>()
    val latestState: LiveData<ServiceState<TMDBLatestMovie>> get() = _latestState
    private val _toolbarTitle = MutableLiveData("")
    val toolbarTitle: LiveData<String> get() = _toolbarTitle

    fun setToolbarTitle(value: String) {
        _toolbarTitle.value = value
    }

    fun randomCover(list: List<TMDBMovie>): String {
        return if(list.isNotEmpty()) {
            val randomData = list.random()
            BuildConfig.IMAGE_URL + randomData.posterPath
        } else ""
    }

    fun randomBackdrop(list: List<TMDBMovie>): String {
        return if(list.isNotEmpty()) {
            val randomData = list.random()
            BuildConfig.IMAGE_URL + randomData.backdropPath
        } else ""
    }

    fun getAll() {
        if(
            !_nowPlayingState.value.isSuccess() &&
            !_topRatedState.value.isSuccess() &&
            !_latestState.value.isSuccess()
        ) {
            realGetAll()
        } else {
            when {
                !_nowPlayingState.value.isSuccess() -> getNowPlayingMovies()
                !_topRatedState.value.isSuccess() -> getTopRatedMovies()
                !_latestState.value.isSuccess() -> getLatestMovies()
                else -> { /*All services successfully consumed*/ }
            }
        }
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
        nowPlayingUseCase.getNowPlayingMoviesAsFlow().onEach { result ->
            result.apply {
                onLoading { _nowPlayingState.postValue(ServiceState.Loading) }
                onFailure { _nowPlayingState.postValue(ServiceState.Failure(FailureModel(it))) }
                onSuccess { _nowPlayingState.postValue(ServiceState.Success(it)) }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopRatedMovies() {
        topRatedUseCase.getTopRatedMoviesAsFlow().onEach { result ->
            result.apply {
                onLoading { _topRatedState.postValue(ServiceState.Loading) }
                onFailure { _topRatedState.postValue(ServiceState.Failure(FailureModel(it))) }
                onSuccess { _topRatedState.postValue(ServiceState.Success(it)) }
            }
        }.launchIn(viewModelScope)
    }

    private fun getLatestMovies() {
        latestUseCase.getLatestAsFlow().onEach { result ->
            result.apply {
                onLoading { _latestState.postValue(ServiceState.Loading) }
                onFailure { _latestState.postValue(ServiceState.Failure(FailureModel(it))) }
                onSuccess { _latestState.postValue(ServiceState.Success(it)) }
            }
        }.launchIn(viewModelScope)
    }
}