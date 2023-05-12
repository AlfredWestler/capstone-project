package com.example.remote.response

import com.example.remote.dto.TMDBGenreDto
import com.squareup.moshi.Json

data class TMDBLatestMovieResponse(
    @Json(name = "adult") val adult: Boolean? = null,
    @Json(name = "budget") val budget: Int? = null,
    @Json(name = "genres") val genres: List<TMDBGenreDto>? = null,
    @Json(name = "homepage") val homepage: String? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "imdb_id") val imdb_id: String? = null,
    @Json(name = "original_language") val original_language: String? = null,
    @Json(name = "original_title") val original_title: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "popularity") val popularity: Int? = null,
    @Json(name = "poster_path") val poster_path: String? = null,
    @Json(name = "release_date") val release_date: String? = null,
    @Json(name = "revenue") val revenue: Int? = null,
    @Json(name = "runtime") val runtime: Int? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "tagline") val tagline: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "video") val video: Boolean? = null,
    @Json(name = "vote_average") val vote_average: Int? = null,
    @Json(name = "vote_count") val vote_count: Int? = null,
    @Json(name = "status_message") val status_message: String? = null,
    @Json(name = "status_code") val status_code: Int? = null
)