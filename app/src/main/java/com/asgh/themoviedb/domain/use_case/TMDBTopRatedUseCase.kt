package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.commons.either.TMDBEither
import com.example.remote.response.TMDBMovieSeriesResponse
import com.asgh.themoviedb.domain.repository.TMDBRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TMDBTopRatedUseCase @Inject constructor(
    private val repository: TMDBRepository
) {
    fun getTopRatedMoviesRx(): Observable<TMDBMovieSeriesResponse> = repository.getTopRatedMoviesRx()

    fun getTopRatedMoviesAsFlow() = flow {
        emit(TMDBEither.Loading)
        repository.getTopRatedMovies().collect{ emit(it) }
    }.catch { emit(TMDBEither.Failure("")) }
}