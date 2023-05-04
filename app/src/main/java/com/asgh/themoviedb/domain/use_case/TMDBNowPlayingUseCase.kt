package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.commons.either.TMDBEither
import com.example.remote.response.TMDBMovieSeriesResponse
import com.asgh.themoviedb.domain.repository.TMDBRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TMDBNowPlayingUseCase @Inject constructor(
    private val repository: TMDBRepository
) {
    fun getNowPlayingMoviesRx(): Observable<TMDBMovieSeriesResponse> =
        repository.getNowPlayingMoviesRx()

    fun getNowPlayingMoviesModified() = flow {
        emit(TMDBEither.Loading)
        repository.getNowPlayingMovies().collect { emit(it) }
    }.catch { emit(TMDBEither.Failure("")) }
}