package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.R
import com.asgh.themoviedb.TMDBApplication
import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.data.remote.response.TMDBLatestMovieResponse
import com.asgh.themoviedb.domain.repository.TMDBRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TMDBLatestUseCase @Inject constructor(
    private val repository: TMDBRepository
) {
    fun getLatestMoviesRx(): Observable<TMDBLatestMovieResponse> = repository.getLatestMoviesRx()
    fun getLatestAsFlow() = flow {
        emit(TMDBEither.Loading)
        repository.getLatestMovies().collect{ emit(it) }
    }.catch { emit(TMDBEither.Failure("")) }
}