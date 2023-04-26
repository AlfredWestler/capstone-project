package com.asgh.themoviedb.domain.repository

import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.data.local.relation.TMDBMovieWithGenres
import com.asgh.themoviedb.data.local.relation.TMDBMoviesInGenre
import com.asgh.themoviedb.data.remote.response.TMDBLatestMovieResponse
import com.asgh.themoviedb.data.remote.response.TMDBMovieSeriesResponse
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {
    fun getLatestMovies(): Flow<TMDBEither<TMDBLatestMovie, String>>
    fun getTopRatedMovies(): Flow<TMDBEither<List<TMDBMovie>, String>>
    fun getNowPlayingMovies(): Flow<TMDBEither<List<TMDBMovie>, String>>

    fun getMovieWithGenres(movieId: Int): Flow<TMDBEither<TMDBMovieWithGenres, String>>
    fun getMoviesInGenre(genreId: Int): Flow<TMDBEither<List<TMDBMoviesInGenre>, String>>

    /**----------------------------------RxJava functions-----------------------------------------*/

    fun getNowPlayingMoviesRx(): Observable<TMDBMovieSeriesResponse>
    fun getLatestMoviesRx(): Observable<TMDBLatestMovieResponse>
    fun getTopRatedMoviesRx(): Observable<TMDBMovieSeriesResponse>
}