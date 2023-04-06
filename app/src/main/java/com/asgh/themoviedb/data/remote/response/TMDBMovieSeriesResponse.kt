package com.asgh.themoviedb.data.remote.response

import com.asgh.themoviedb.data.remote.dto.TMDBDatesDto
import com.asgh.themoviedb.data.remote.dto.TMDBMovieSeriesResultDto
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.model.TMDBTvShow
import com.squareup.moshi.Json

data class TMDBMovieSeriesResponse(
    @Json(name = "dates") val dates : TMDBDatesDto? = null,
    @Json(name = "page") val page : Int? = null,
    @Json(name = "results") val results : List<TMDBMovieSeriesResultDto>? = null,
    @Json(name = "total_pages") val total_pages : Int? = null,
    @Json(name = "total_results") val total_results : Int? = null,
    @Json(name = "status_message")val status_message: String? = null,
    @Json(name = "status_code")val status_code: Int? = null,
) {

    fun toMovies(): List<TMDBMovie> {
//        val u = results?.asSequence()?.map { it.toMovie() } // TODO: Implementar sequencias en lugar de list
        return results?.map { it.toMovie() } ?: emptyList()
    }

    fun toTvShows(): List<TMDBTvShow> {
        return results?.map { it.toTvShow() } ?: emptyList()
    }
}