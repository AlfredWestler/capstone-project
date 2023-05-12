package com.asgh.themoviedb.domain.repository

import com.asgh.themoviedb.commons.either.TMDBEither
import com.example.local.relation.TMDBMovieWithGenres
import com.example.local.relation.TMDBMoviesInGenre
import com.example.remote.response.TMDBLatestMovieResponse
import com.example.remote.response.TMDBMovieSeriesResponse
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

    fun getMovieById(id:Int): Flow<TMDBEither<TMDBMovie, String>>

    /**----------------------------------RxJava functions-----------------------------------------*/

    fun getNowPlayingMoviesRx(): Observable<TMDBMovieSeriesResponse>
    fun getLatestMoviesRx(): Observable<TMDBLatestMovieResponse>
    fun getTopRatedMoviesRx(): Observable<TMDBMovieSeriesResponse>
}