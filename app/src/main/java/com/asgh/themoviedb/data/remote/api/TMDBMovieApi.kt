package com.asgh.themoviedb.data.remote.api

import com.asgh.themoviedb.data.remote.response.TMDBLatestMovieResponse
import com.asgh.themoviedb.data.remote.response.TMDBMovieSeriesResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBMovieApi {

    @GET("3/{type}/{endPoint}")
    suspend fun getMoviesOrSeries(
        @Path("type") type: String,
        @Path("endPoint") endPoint: String
    ): Response<TMDBMovieSeriesResponse>

    @GET("3/{type}/{endPoint}")
    suspend fun getLatestMoviesOrSeries(
        @Path("type") type: String,
        @Path("endPoint") endPoint: String = TMDBEndPoint.LATEST.endPoint
    ): Response<TMDBLatestMovieResponse>

    @GET("3/genre/{type}/{endPoint}")
    suspend fun getMoviesGenreList(
        @Path("type") type: String = TMDBApiInfoType.MOVIES.type,
        @Path("endPoint") endPoint: String = TMDBEndPoint.LIST.endPoint
    ): Response<TMDBLatestMovieResponse>


    /**----------------------------------RxJava Consumes------------------------------------------*/

    @GET("3/{type}/{endPoint}")
    fun getMoviesOrSeriesRx(
        @Path("type") type: String,
        @Path("endPoint") endPoint: String
    ): Observable<TMDBMovieSeriesResponse>

    @GET("3/{type}/{endPoint}")
    fun getLatestMoviesOrSeriesRx(
        @Path("type") type: String,
        @Path("endPoint") endPoint: String = TMDBEndPoint.LATEST.endPoint
    ): Observable<TMDBLatestMovieResponse>
}