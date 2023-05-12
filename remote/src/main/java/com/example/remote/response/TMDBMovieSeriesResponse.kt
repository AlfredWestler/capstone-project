package com.example.remote.response

import com.example.remote.dto.TMDBDatesDto
import com.example.remote.dto.TMDBMovieSeriesResultDto
import com.squareup.moshi.Json

data class TMDBMovieSeriesResponse(
    @Json(name = "dates") val dates : TMDBDatesDto? = null,
    @Json(name = "page") val page : Int? = null,
    @Json(name = "results") val results : List<TMDBMovieSeriesResultDto>? = null,
    @Json(name = "total_pages") val total_pages : Int? = null,
    @Json(name = "total_results") val total_results : Int? = null,
    @Json(name = "status_message")val status_message: String? = null,
    @Json(name = "status_code")val status_code: Int? = null,
)