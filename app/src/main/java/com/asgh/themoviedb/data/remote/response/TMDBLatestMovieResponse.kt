package com.asgh.themoviedb.data.remote.response

import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.data.local.entity.TMDBLatestMovieEntity
import com.asgh.themoviedb.data.remote.dto.TMDBGenreDto
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
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
) {

    fun toLatestMovie() = TMDBLatestMovie(
        adult = adult ?: false,
        budget = budget ?: 0,
        genres = genres?.map { it.toGenre() }?.toList() ?: emptyList(),
        homepage = homepage.orEmpty(),
        id = id ?: 0,
        imdb_id = imdb_id.orEmpty(),
        original_language = original_language.orEmpty(),
        original_title = original_title.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0,
        poster_path = "${BuildConfig.IMAGE_URL}$poster_path",
        release_date = release_date.orEmpty(),
        revenue = revenue ?: 0,
        runtime = runtime ?: 0,
        status = status.orEmpty(),
        tagline = tagline.orEmpty(),
        title = title.orEmpty(),
        video = video ?: false,
        vote_average = vote_average ?: 0,
        vote_count = vote_count ?: 0,
        status_message = status_message.orEmpty(),
        status_code = status_code ?: 0
    )

    fun toLatestMovieEntity() = TMDBLatestMovieEntity(
        id = 0,
        adult = adult ?: false,
        budget = budget ?: 0,
        homepage = homepage.orEmpty(),
        movieId = id ?: 0,
        imdb_id = imdb_id.orEmpty(),
        original_language = original_language.orEmpty(),
        original_title = original_title.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0,
        poster_path = "${BuildConfig.IMAGE_URL}$poster_path",
        release_date = release_date.orEmpty(),
        revenue = revenue ?: 0,
        runtime = runtime ?: 0,
        status = status.orEmpty(),
        tagline = tagline.orEmpty(),
        title = title.orEmpty(),
        video = video ?: false,
        vote_average = vote_average ?: 0,
        vote_count = vote_count ?: 0,
        status_message = status_message.orEmpty(),
        status_code = status_code ?: 0
    )
}