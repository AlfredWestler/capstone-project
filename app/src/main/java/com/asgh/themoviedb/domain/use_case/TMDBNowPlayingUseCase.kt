package com.asgh.themoviedb.domain.use_case

import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.data.remote.response.TMDBMovieSeriesResponse
import com.asgh.themoviedb.domain.model.TMDBMovie
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

    fun getNowPlayingMoviesAsFlow() = flow<TMDBEither<List<TMDBMovie>,String>> {
        emit(TMDBEither.Loading)
        val result = repository.getNowPlayingMovies()
        if(result.isSuccessful) {
            emit(TMDBEither.Success(result.body()?.toMovies() ?: emptyList()))
        } else {
            emit(TMDBEither.Failure("Looks like the aliens abducted the info"))
        }
    }.catch { emit(TMDBEither.Failure("Looks like the aliens abducted the info")) }
}