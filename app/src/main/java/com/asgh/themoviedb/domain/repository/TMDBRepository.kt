package com.asgh.themoviedb.domain.repository

import com.asgh.themoviedb.data.remote.response.TMDBLatestMovieResponse
import com.asgh.themoviedb.data.remote.response.TMDBMovieSeriesResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

interface TMDBRepository {

    suspend fun getNowPlayingMovies(): Response<TMDBMovieSeriesResponse>
    suspend fun getLatestMovies(): Response<TMDBLatestMovieResponse>
    suspend fun getTopRatedMovies(): Response<TMDBMovieSeriesResponse>

    /**----------------------------------RxJava functions-----------------------------------------*/

    fun getNowPlayingMoviesRx(): Observable<TMDBMovieSeriesResponse>
    fun getLatestMoviesRx(): Observable<TMDBLatestMovieResponse>
    fun getTopRatedMoviesRx(): Observable<TMDBMovieSeriesResponse>
}