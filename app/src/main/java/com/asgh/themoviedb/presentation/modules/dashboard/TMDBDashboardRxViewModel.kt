package com.asgh.themoviedb.presentation.modules.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class TMDBDashboardRxViewModel @Inject constructor(
    private val nowPlayingUseCase: TMDBNowPlayingUseCase,
    private val topRatedUseCase: TMDBTopRatedUseCase,
    private val latestUseCase: TMDBLatestUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

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
                !_nowPlayingState.value.isSuccess() -> getNowPlayingMoviesRx()
                !_topRatedState.value.isSuccess() -> getTopRatedMoviesRx()
                !_latestState.value.isSuccess() -> getLatestMoviesRx()
                else -> { /*All services successfully consumed*/ }
            }
        }
    }

    private fun realGetAll() {
        getNowPlayingMoviesRx()
        getTopRatedMoviesRx()
        getLatestMoviesRx()
    }

    private fun getNowPlayingMoviesRx() {
        _nowPlayingState.postValue(ServiceState.Loading)
        val disposable = nowPlayingUseCase.getNowPlayingMoviesRx()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _nowPlayingState.postValue(ServiceState.Success(it.toMovies()))
                },
                {
                    _nowPlayingState.postValue(ServiceState.Failure(
                        FailureModel("Looks like the aliens abducted this info")
                    ))
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun getLatestMoviesRx() {
        _latestState.postValue(ServiceState.Loading)
        val disposable = latestUseCase.getLatestMoviesRx()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _latestState.postValue(ServiceState.Success(it.toLatestMovie()))
                },
                {
                    _latestState.postValue(ServiceState.Failure(
                        FailureModel(it.message.orEmpty())
                    ))
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun getTopRatedMoviesRx() {
        _topRatedState.postValue(ServiceState.Loading)
        val disposable = topRatedUseCase.getTopRatedMoviesRx()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _topRatedState.postValue(ServiceState.Success(it.toMovies()))
                },
                {
                    _topRatedState.postValue(ServiceState.Failure(
                        FailureModel(it.message.orEmpty())
                    ))
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}